package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RegisteredVisitorServiceImpl implements RegisteredVisitorService{

    private final RegisteredVisitorRepository registeredVisitorRepository;

    private final PasswordEncoder passwordEncoder;

    public RegisteredVisitorServiceImpl(RegisteredVisitorRepository registeredVisitorRepository, PasswordEncoder passwordEncoder) {
        this.registeredVisitorRepository = registeredVisitorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
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
    @Transactional
    public RegisteredVisitor findById(Long id) {
        return registeredVisitorRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void updateVisitorWithoutPwd(RegisteredVisitor registeredVisitor) {

        //TODO: uncomment and correct the condition: condition is not correct as we should exclude the email address from the registeredVisitor
//        if (emailExists(registeredVisitor.getEmailAddress())) {
//            throw new UserAlreadyExistException("There is an user with that email address: " + registeredVisitor.getEmailAddress());
//        }
//
//        if (usernameExists(registeredVisitor.getUsername())) {
//            throw new UserAlreadyExistException("There is an user with that username: " + registeredVisitor.getUsername());
//        }

        registeredVisitorRepository.updateVisitorWithoutPwd(registeredVisitor.getId(),
                registeredVisitor.getUsername(), registeredVisitor.getFirstName(),
                registeredVisitor.getLastName(), registeredVisitor.getEmailAddress(),
                registeredVisitor.getIsWriter(), registeredVisitor.getGender());
    }

    @Override
    @Transactional
    public void updateVisitorWithPwd(Long id, String username, String firstName, String lastName, String email, Boolean writer, String password) {
        registeredVisitorRepository.updateVisitorWithPwd(id, username, firstName, lastName, email, writer, passwordEncoder.encode(password));
    }

    private boolean emailExists(final String email) {
        return registeredVisitorRepository.findByEmailAddress(email) != null;
    }

    private boolean usernameExists(final String username) {
        return registeredVisitorRepository.findByUsername(username) != null;
    }

}
