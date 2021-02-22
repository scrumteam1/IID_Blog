package be.intecbrussel.iddblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;



    @Override
    protected void configure(final AuthenticationManagerBuilder amb) throws Exception {
        amb.jdbcAuthentication()
                .dataSource(dataSource);

    }

    //the h2-console is now attainable by the devs but also a random user
    //remove this for end product
    @Override
    protected void configure (final HttpSecurity httpSecurity) throws Exception {
        //disable security to not asking to login at this time (not needed for signup)
        httpSecurity.httpBasic().disable();



        httpSecurity.authorizeRequests()
                    .antMatchers("/", "/index/**","/registerform/**","/registeredvisitor/**").permitAll()
                    .and()
                    .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/index");

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
