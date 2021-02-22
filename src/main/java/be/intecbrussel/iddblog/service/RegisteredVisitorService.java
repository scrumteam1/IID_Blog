package be.intecbrussel.iddblog.service;


import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import org.springframework.data.repository.query.Param;


public interface RegisteredVisitorService {

    RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    RegisteredVisitor findById(Long id);

    void updateVisitorWithoutPwd( RegisteredVisitor registeredVisitor);

    void updateVisitorWithPwd( Long id,  String username,
                               String firstName,  String lastName,
                               String email,
                               Boolean writer, String password);

}
