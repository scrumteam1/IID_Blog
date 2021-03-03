package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.Authority;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<Authority, Long> {

    @Modifying
    @Query("DELETE FROM Authority a WHERE a.username = :username")
    void deleteAllByRegisteredVisitor(@Param(value = "username") String username);

}
