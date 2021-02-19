package be.intecbrussel.iddblog.service;


import be.intecbrussel.iddblog.domain.RegisteredVisitor;



public interface RegisteredVisitorService {

    RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    RegisteredVisitor findById(Long id);

    void updateVisitorWithPwd( Long id,  String username,
                               String firstName,  String lastName,
                               String email,
                               Boolean writer);

}
