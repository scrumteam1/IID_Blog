package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RegisteredVisitorService {

    RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    RegisteredVisitor findById(Long id);

    RegisteredVisitor findByUsername(String username);

    RegisteredVisitor findByEmailAddress(String email);

    List<RegisteredVisitor> findAll(String keyword);

    void updateVisitorWithoutPwd( RegisteredVisitor registeredVisitor);

    void updateUserPwd(@Param(value = "id") Long id, @Param(value = "password") String password);

    boolean checkIfValidOldPassword(RegisteredVisitor visitor, String oldPassword);

    void deleteVisitor(String registeredVisitor);

    void createVerificationToken(RegisteredVisitor visitor, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void updateUserEnabled(RegisteredVisitor visitor, boolean enabled);

}
