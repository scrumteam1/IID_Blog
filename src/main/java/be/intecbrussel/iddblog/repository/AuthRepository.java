package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AuthRepository extends CrudRepository<Authority, Long> {

    Authority save(Authority authority);

    @Query(value = "select a.* from authorities a, user_authority ua, users u\n" +
            "where ua.auth_id = a.id and u.id = ua.user_id and u.username=:username", nativeQuery = true)
    List<Authority> findAuthorityByUsername( @Param(value="username") String username);

}
