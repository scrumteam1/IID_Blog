package be.intecbrussel.iddblog.service;

import org.springframework.data.repository.query.Param;

public interface AuthService {

    void deleteAllByRegisteredVisitor(String username);

    void updateAuthority(String username, String authority);

    String findAuthorityByUsername(String username);
}
