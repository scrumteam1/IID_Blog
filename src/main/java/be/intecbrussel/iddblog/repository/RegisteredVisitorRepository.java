package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredVisitorRepository extends CrudRepository<RegisteredVisitor, Long> {

    RegisteredVisitor findByEmailAddress(String email);

    RegisteredVisitor findByUsername(String username);
}
