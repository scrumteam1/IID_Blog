package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public Authority save(Authority authority) {
        return authRepository.save(authority);
    }

    @Override
    public List<Authority> findAuthorityByUsername(String username) {
        return authRepository.findAuthorityByUsername(username);
    }
}
