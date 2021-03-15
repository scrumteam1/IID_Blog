package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Authority;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthService {

    Authority save(Authority authority);

    List<Authority> findAuthorityByUsername(@Param(value="username") String username);
}
