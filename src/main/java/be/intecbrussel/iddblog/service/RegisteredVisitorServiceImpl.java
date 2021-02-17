package be.intecbrussel.iddblog.service;


import be.intecbrussel.iddblog.command.RegisteredVisitorCommand;
import be.intecbrussel.iddblog.converter.RegisteredVisitorCommandToRegisteredVisitor;
import be.intecbrussel.iddblog.converter.RegisteredVisitorToRegisteredVisitorCommand;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RegisteredVisitorServiceImpl implements RegisteredVisitorService{

    private final RegisteredVisitorRepository registeredVisitorRepository;
    private final RegisteredVisitorCommandToRegisteredVisitor registeredVisitorCommandToRegisteredVisitor;
    private final RegisteredVisitorToRegisteredVisitorCommand registeredVisitorToRegisteredVisitorCommand;


    public RegisteredVisitorServiceImpl(RegisteredVisitorRepository registeredVisitorRepository, RegisteredVisitorCommandToRegisteredVisitor registeredVisitorCommandToRegisteredVisitor,
                                        RegisteredVisitorToRegisteredVisitorCommand registeredVisitorToRegisteredVisitorCommand) {
        this.registeredVisitorRepository = registeredVisitorRepository;
        this.registeredVisitorCommandToRegisteredVisitor = registeredVisitorCommandToRegisteredVisitor;
        this.registeredVisitorToRegisteredVisitorCommand = registeredVisitorToRegisteredVisitorCommand;
    }

    @Override
    @Transactional
    public RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor) {

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

}
