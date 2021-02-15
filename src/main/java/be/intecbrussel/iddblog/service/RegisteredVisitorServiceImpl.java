package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RegisteredVisitorServiceImpl implements RegisteredVisitorService{

    private final RegisteredVisitorRepository registeredVisitorRepository;

    public RegisteredVisitorServiceImpl(RegisteredVisitorRepository registeredVisitorRepository) {
        this.registeredVisitorRepository = registeredVisitorRepository;
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
}
