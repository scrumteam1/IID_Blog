package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.repository.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void deleteAllByRegisteredVisitor(String username) {
        authRepository.deleteAllByRegisteredVisitor(username);
    }

    @Override
    public void updateAuthority(String username, String authority) {
        authRepository.updateAuthority(username, authority);
    }

    @Override
    public String findAuthorityByUsername(String username) {
        return authRepository.findAuthorityByUsername(username);
    }
}
