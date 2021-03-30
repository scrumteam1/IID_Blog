package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisteredVisitorRepository extends JpaRepository<RegisteredVisitor, Long> {

    RegisteredVisitor save(RegisteredVisitor visitor);

    Optional<RegisteredVisitor> findById(Long id);

    RegisteredVisitor findByEmailAddress(String email);

    RegisteredVisitor findByUsername(String username);

    Page<RegisteredVisitor> findAll(Pageable pageable);

    @Query("select u from RegisteredVisitor u where " +
            "concat(u.id, u.username, u.emailAddress, u.firstName, u.lastName)" +
            " like %:keyword%")
    Page<RegisteredVisitor> findAll(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select u.* from `users` u\n" +
            "left join `user_authority` ua on u.id = ua.user_id\n" +
            "left outer join `authorities` a on a.id = ua.auth_id\n" +
            "where a.authority != 'ADMIN'", nativeQuery = true)
    Page<RegisteredVisitor> findAllExceptAdmin(Pageable pageable);

    @Query(value = "select u.* from users u\n" +
            "left join user_authority ua on u.id = ua.user_id\n" +
            "left outer join authorities a on a.id = ua.auth_id\n" +
            "where a.authority != 'ADMIN'\n" +
            "and (u.id like concat('%',:keyword,'%') or u.username like concat('%',:keyword,'%') " +
            "or u.email_address like concat('%',:keyword,'%') or u.first_name like concat('%',:keyword,'%') " +
            "or u.last_name like concat('%',:keyword,'%'))", nativeQuery = true)
    Page<RegisteredVisitor> findAllExceptAdmin(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("update RegisteredVisitor u set u.username = :username, u.firstName = :firstName, u.lastName = :lastName , " +
            "u.emailAddress = :email, u.isWriter = :writer, u.gender = :gender where u.id = :id")
    void updateVisitorWithoutPwd(@Param(value = "id") Long id, @Param(value = "username") String username,
                                 @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName,
                                 @Param(value = "email") String email, @Param(value = "writer") Boolean writer,
                                 @Param(value = "gender") String gender);


    @Modifying
    @Query("update RegisteredVisitor u set u.encodedPassword = :password where u.id = :id")
    void updateUserPwd(@Param(value = "id") Long id, @Param(value = "password") String password);

    @Query("select u from RegisteredVisitor u where u.emailAddress = :email and u.id <> :id")
    List<RegisteredVisitor> findByEmailNotEqualToId(@Param(value = "email") String email, @Param(value = "id") Long id);

    @Query("select u from RegisteredVisitor u where u.username = :username and u.id <> :id")
    List<RegisteredVisitor> findByUsernameNotEqualToId(@Param(value = "username") String username,  @Param(value = "id") Long id);

    void deleteByUsername(String username);

    @Modifying
    @Query("update RegisteredVisitor u set u.enabled = :enabled where u.id = :id")
    void updateUserEnabled(@Param(value = "id") Long id, @Param(value = "enabled") boolean enabled);
}


