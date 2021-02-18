package be.intecbrussel.iddblog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder amb) throws Exception {

        amb.inMemoryAuthentication()
           .withUser("user").password(passwordEncoder().encode("user123")).roles("USER")
           .and()
           .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN", "USER");

    }

    @Override
    protected void configure (final HttpSecurity httpSecurity) throws Exception{


//        httpSecurity.authorizeRequests()
//                    .antMatchers("/admin").hasRole("ADMIN")
//                    .antMatchers("/user").hasRole("USER")
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
