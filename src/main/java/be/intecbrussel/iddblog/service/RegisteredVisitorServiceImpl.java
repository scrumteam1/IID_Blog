package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import be.intecbrussel.iddblog.repository.VerifTokenRepository;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class RegisteredVisitorServiceImpl implements RegisteredVisitorService{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RegisteredVisitorRepository registeredVisitorRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    private final VerifTokenRepository tokenRepository;

    private final WriterService writerService;


    public RegisteredVisitorServiceImpl(NamedParameterJdbcTemplate jdbcTemplate, RegisteredVisitorRepository registeredVisitorRepository,
                                        PasswordEncoder passwordEncoder, AuthService authService, VerifTokenRepository tokenRepository,
                                        WriterService writerService) {

        this.jdbcTemplate = jdbcTemplate;
        this.registeredVisitorRepository = registeredVisitorRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.tokenRepository = tokenRepository;
        this.writerService = writerService;
    }

    @Override
    public RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor) {

        if (emailExists(registeredVisitor.getEmailAddress())) {
            throw new UserAlreadyExistException("There is an user with that email address: " + registeredVisitor.getEmailAddress());
        }

        if (usernameExists(registeredVisitor.getUsername())) {
            throw new UserAlreadyExistException("There is an user with that username: " + registeredVisitor.getUsername());
        }

        // Only the encoded password (not the password and confirm password) will be saved to the db
        String encodedPwd = passwordEncoder.encode(registeredVisitor.getPassword());
        registeredVisitor.setEncodedPassword(encodedPwd);

        RegisteredVisitor savedVisitor = registeredVisitorRepository.save(registeredVisitor);
        log.debug("Saved VisitorId:" + savedVisitor.getId());
        return savedVisitor;
    }

    @Override
    public RegisteredVisitor findById(Long id) {
        return registeredVisitorRepository.findById(id).orElse(null);
    }

    @Override
    public RegisteredVisitor findByUsername(String username) {
        return registeredVisitorRepository.findByUsername(username);
    }

    @Override
    public RegisteredVisitor findByEmailAddress(String email) {
        return registeredVisitorRepository.findByEmailAddress(email);
    }

    @Override
    public Page<RegisteredVisitor> findAll(String keyword, int pageNumber, String sortField, String sortDir) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 ,5, sort);

        if(keyword != null) {
            return registeredVisitorRepository.findAll(keyword, pageable);
        }
        return registeredVisitorRepository.findAll(pageable);
    }

    @Override
    public void updateVisitorWithoutPwd(RegisteredVisitor registeredVisitor) {

        if (emailExistsForIdOtherThanCurrentId(registeredVisitor.getEmailAddress(),registeredVisitor.getId())) {
            throw new UserAlreadyExistException("There is an user with that email address: " + registeredVisitor.getEmailAddress());
        }

        if (usernameExistsForIdOtherThanCurrentId(registeredVisitor.getUsername(),registeredVisitor.getId())) {
            throw new UserAlreadyExistException("There is an user with that username: " + registeredVisitor.getUsername());
        }

        registeredVisitorRepository.updateVisitorWithoutPwd(registeredVisitor.getId(),
                registeredVisitor.getUsername(), registeredVisitor.getFirstName(),
                registeredVisitor.getLastName(), registeredVisitor.getEmailAddress(),
                registeredVisitor.getIsWriter(), registeredVisitor.getGender());

        RegisteredVisitor udpatedVisitor = registeredVisitorRepository.findById(registeredVisitor.getId()).get();

        Authority authority = authService.findAuthorityByUsername(udpatedVisitor.getUsername()).get(0);

        if(registeredVisitor.getIsWriter() && !authority.getAuthority().equals("ADMIN")) {
            authority.setAuthority("WRITER");
        } else if (!authority.getAuthority().equals("ADMIN")) {
            authority.setAuthority("USER");
        }

        authService.save(authority);

        List<WriterPost> posts = writerService.findWriterPostsByUserId(udpatedVisitor.getId());
        posts.forEach(p -> log.warn("post: " + p.getTitle()));

        // if admin then will not have posts and posts.size will zero
        if(registeredVisitor.getIsWriter() && posts.size()>0) {
            posts.forEach(p -> {
                p.setIsEnabled(true);
                p.setRegisteredVisitor(udpatedVisitor);
                writerService.save(p);
            });

        } else if(!registeredVisitor.getIsWriter() && posts.size()>0) {
            posts.forEach(p -> {
                p.setIsEnabled(false);
                p.setRegisteredVisitor(udpatedVisitor);
                writerService.save(p);
            });
        }

    }

    @Override
    public void updateUserPwd(Long id, String password) {
        registeredVisitorRepository.updateUserPwd(id, passwordEncoder.encode(password));
    }

    public boolean checkIfValidOldPassword(RegisteredVisitor visitor, String oldPassword) {

        return passwordEncoder.matches(oldPassword,visitor.getEncodedPassword());
    }

    @Override
    @Transactional
    public void deleteVisitor(String username) {
        registeredVisitorRepository.deleteByUsername(username);
    }

    private boolean emailExists(final String email) {
        return registeredVisitorRepository.findByEmailAddress(email) != null;
    }

    private boolean usernameExists(final String username) {
        return registeredVisitorRepository.findByUsername(username) != null;
    }

    private boolean emailExistsForIdOtherThanCurrentId(final String email, final Long id) {
        int sizeUsers = registeredVisitorRepository.findByEmailNotEqualToId(email, id).size();
        return  sizeUsers > 0;
    }

    private boolean usernameExistsForIdOtherThanCurrentId(final String username, final Long id) {
        int sizeUsers = registeredVisitorRepository.findByUsernameNotEqualToId(username, id).size();
        return  sizeUsers > 0;
    }

    @Override
    public void createVerificationToken(RegisteredVisitor visitor, String token) {
        VerificationToken myToken = new VerificationToken(token, visitor);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void updateUserEnabled(RegisteredVisitor visitor, boolean enabled) {
        registeredVisitorRepository.updateUserEnabled(visitor.getId(), enabled);
    }
}
