package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.repository.AuthRepository;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import be.intecbrussel.iddblog.repository.VerifTokenRepository;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class RegisteredVisitorServiceImpl implements RegisteredVisitorService{

    private final RegisteredVisitorRepository registeredVisitorRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthRepository authRepository;

    private final VerifTokenRepository tokenRepository;

    public RegisteredVisitorServiceImpl(RegisteredVisitorRepository registeredVisitorRepository,
                                        PasswordEncoder passwordEncoder, AuthRepository authRepository,
                                        VerifTokenRepository tokenRepository) {
        this.registeredVisitorRepository = registeredVisitorRepository;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
        this.tokenRepository = tokenRepository;
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
        return registeredVisitorRepository.findById(id).get();
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
    public List<RegisteredVisitor> findAll() {
        return registeredVisitorRepository.findAll();
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
    }

    @Override
    public void updateVisitorWithPwd(Long id, String username, String firstName, String lastName, String email, Boolean writer, String password) {
        registeredVisitorRepository.updateVisitorWithPwd(id, username, firstName, lastName, email, writer, passwordEncoder.encode(password));
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
        authRepository.deleteAllByRegisteredVisitor(username);
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
    public void createAuthority(RegisteredVisitor visitor, String authority) {
        Authority myauthority = Authority.builder()
                .registeredVisitor(visitor)
                .username(visitor.getUsername())
                .authority(authority)
                .build();
        authRepository.save(myauthority);
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
