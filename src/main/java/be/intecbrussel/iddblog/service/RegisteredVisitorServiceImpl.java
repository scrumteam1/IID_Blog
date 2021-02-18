package be.intecbrussel.iddblog.service;


import be.intecbrussel.iddblog.command.RegisteredVisitorCommand;
import be.intecbrussel.iddblog.converter.RegisteredVisitorCommandToRegisteredVisitor;
import be.intecbrussel.iddblog.converter.RegisteredVisitorToRegisteredVisitorCommand;
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
    private final RegisteredVisitorCommandToRegisteredVisitor registeredVisitorCommandToRegisteredVisitor;
    private final RegisteredVisitorToRegisteredVisitorCommand registeredVisitorToRegisteredVisitorCommand;

    private final PasswordEncoder passwordEncoder;

    public RegisteredVisitorServiceImpl(RegisteredVisitorRepository registeredVisitorRepository, RegisteredVisitorCommandToRegisteredVisitor registeredVisitorCommandToRegisteredVisitor,
                                        RegisteredVisitorToRegisteredVisitorCommand registeredVisitorToRegisteredVisitorCommand,
                                        PasswordEncoder passwordEncoder) {

        this.registeredVisitorRepository = registeredVisitorRepository;
        this.registeredVisitorCommandToRegisteredVisitor = registeredVisitorCommandToRegisteredVisitor;
        this.registeredVisitorToRegisteredVisitorCommand = registeredVisitorToRegisteredVisitorCommand;
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
    public RegisteredVisitorCommand updateVisitorCommand(RegisteredVisitorCommand command) {
        RegisteredVisitor detachedVisitor = registeredVisitorCommandToRegisteredVisitor.convert(command);

        RegisteredVisitor updateVisitor = registeredVisitorRepository.save(detachedVisitor);
        log.debug("Updated RecipeId: " + updateVisitor.getId());

        return registeredVisitorToRegisteredVisitorCommand.convert(updateVisitor);
    }

    private boolean emailExists(final String email) {
        return registeredVisitorRepository.findByEmailAddress(email) != null;
    }

    private boolean usernameExists(final String username) {
        return registeredVisitorRepository.findByUsername(username) != null;
    }

}
