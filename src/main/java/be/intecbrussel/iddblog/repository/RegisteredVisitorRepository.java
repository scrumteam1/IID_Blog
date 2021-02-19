package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredVisitorRepository extends CrudRepository<RegisteredVisitor, Long> {

    RegisteredVisitor findByEmailAddress(String email);

    @Query("SELECT u FROM RegisteredVisitor u WHERE u.username = ?1")
    RegisteredVisitor findByUsername(String username);

    @Modifying
    @Query("update RegisteredVisitor u set u.username = :username, u.firstName = :firstName, u.lastName = :lastName , u.emailAddress = :email, u.isWriter = :writer  where u.id = :id")
    void updateVisitorWithPwd(@Param(value = "id") Long id, @Param(value = "username") String username,
                           @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
                           @Param(value = "email") String email,
                           @Param(value = "writer") Boolean writer);
}
