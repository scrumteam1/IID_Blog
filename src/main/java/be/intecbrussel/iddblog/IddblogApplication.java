package be.intecbrussel.iddblog;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.AuthRepository;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class IddblogApplication {
    @Autowired
    private RegisteredVisitorRepository visitorRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(IddblogApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerWhenStarts() {

//        List<Authority> authorities = authRepository.findAuthorityByUsername("ab3");
//        authorities.get(0).setAuthority("USER");
//        authRepository.save(authorities.get(0));
//        authorities.forEach(p->log.warn("authority: " + p.getAuthority()));


//        addUser("ab1");

//        deleteUser("ab1");

    }

//    private void addUser(String username) {
//        RegisteredVisitor visitor = RegisteredVisitor.builder()
//                .username(username)
//                .emailAddress(username+"@hotmail.com")
//                .isWriter(true)
//                .encodedPassword(passwordEncoder.encode("!nXkTT7C4#DNiU"))
//                .password("!nXkTT7C4#DNiU")
//                .confirmPassword("!nXkTT7C4#DNiU")
//                .firstName("abd1")
//                .lastName("kh1")
//                .build();
//
//        List<RegisteredVisitor> visitors = new ArrayList<>();
//        visitors.add(visitor);
//        Authority authority = Authority.builder()
//                .authority("WRITER")
//                .registeredVisitors(visitors).build();
//
//        List<Authority> authorities = new ArrayList<>();
//        authorities.add(authority);
//        visitor.setAuthority(authorities);
//
//        visitorRepository.save(visitor);
//
//        log.warn("user created: " + visitor);
//    }

    private void deleteUser(String username) {
        RegisteredVisitor visitor = visitorRepository.findByUsername(username);
        visitorRepository.delete(visitor);
    }

}
