package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.Authority;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface AuthRepository extends CrudRepository<Authority, Long> {

    @Modifying
    @Query("DELETE FROM Authority a WHERE a.username = :username")
    void deleteAllByRegisteredVisitor(@Param(value = "username") String username);

    @Modifying
    @Query("update Authority a set a.authority = :authority  where a.username = :username")
    void updateAuthority(@Param(value = "username") String username, @Param(value = "authority") String authority);

    @Query("select a.authority from Authority a where a.username = :username")
    String findAuthorityByUsername(String username);
}
