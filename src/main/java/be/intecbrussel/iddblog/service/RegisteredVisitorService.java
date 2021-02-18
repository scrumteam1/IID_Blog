package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.command.RegisteredVisitorCommand;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;


public interface RegisteredVisitorService {

    RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    RegisteredVisitor findById(Long id);

    RegisteredVisitorCommand  updateVisitorCommand(RegisteredVisitorCommand command);

}
