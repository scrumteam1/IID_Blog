package be.intecbrussel.iddblog.service;


import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.domain.WriterPost;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RegisteredVisitorService {

    RegisteredVisitor saveVisitor(RegisteredVisitor registeredVisitor);

    RegisteredVisitor findById(Long id);

    RegisteredVisitor findByUsername(String username);

    RegisteredVisitor findByEmailAddress(String email);

    void updateVisitorWithoutPwd( RegisteredVisitor registeredVisitor);

    void updateVisitorWithPwd( Long id,  String username,
                               String firstName,  String lastName,
                               String email,
                               Boolean writer, String password);

    void updateUserPwd(@Param(value = "id") Long id, @Param(value = "password") String password);

    boolean checkIfValidOldPassword(RegisteredVisitor visitor, String oldPassword);

    void deleteVisitor(String registeredVisitor);

    void createAuthority(RegisteredVisitor visitor, String authority);

    void createVerificationToken(RegisteredVisitor visitor, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void updateUserEnabled(RegisteredVisitor visitor, boolean enabled);

    List<WriterPost> findWriterPostsByUserId(long userId);
}
