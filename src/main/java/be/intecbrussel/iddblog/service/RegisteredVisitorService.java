package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import org.springframework.data.domain.Page;

public interface RegisteredVisitorService {

    RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    RegisteredVisitor findById(Long id);

    RegisteredVisitor findByUsername(String username);

    RegisteredVisitor findByEmailAddress(String email);

    Page<RegisteredVisitor> findAllExceptAdmin(String keyword, int pageNumber, String sortField, String sortDir);

    void updateVisitorWithoutPwd( RegisteredVisitor registeredVisitor);

    void updateUserPwd(Long id,String password);

    boolean checkIfValidOldPassword(RegisteredVisitor visitor, String oldPassword);

    void deleteVisitor(String registeredVisitor);

    void createVerificationToken(RegisteredVisitor visitor, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void updateUserEnabled(RegisteredVisitor visitor, boolean enabled);

}
