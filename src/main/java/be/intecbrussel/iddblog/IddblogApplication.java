package be.intecbrussel.iddblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class IddblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(IddblogApplication.class, args);
    }

}
