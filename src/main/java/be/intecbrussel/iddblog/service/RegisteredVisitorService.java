package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import org.springframework.stereotype.Service;

public interface RegisteredVisitorService {

    public RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    public RegisteredVisitor findById(Long id);
}
