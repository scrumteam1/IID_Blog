package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;


public interface RegisteredVisitorService {

    public RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    public RegisteredVisitor findById(Long id);
}
