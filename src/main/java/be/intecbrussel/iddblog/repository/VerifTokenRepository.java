package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.VerificationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    @Modifying
    @Query("delete from VerificationToken v where v.id = :id")
    void deleteById(@Param(value = "id") Long id);
}