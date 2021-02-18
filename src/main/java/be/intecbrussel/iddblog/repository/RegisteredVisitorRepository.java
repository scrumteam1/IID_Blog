package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredVisitorRepository extends CrudRepository<RegisteredVisitor, Long> {

    RegisteredVisitor findByEmailAddress(String email);

    @Query("SELECT u FROM RegisteredVisitor u WHERE u.username = ?1")
    RegisteredVisitor findByUsername(String username);
}
