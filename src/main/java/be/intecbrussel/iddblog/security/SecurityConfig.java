package be.intecbrussel.iddblog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

    //the h2-console is now attainable by the devs but also a random user
    //remove this for end product
    @Override
    protected void configure (final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                    .antMatchers("/", "/index/**","/registerform/**","/registeredvisitor/**").permitAll()
                    .and()
                    .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login-error")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/index")
                    .permitAll();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**","/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
