package be.intecbrussel.iddblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {

        security
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/registeredvisitor/**","/registerform/**","/login",
                        "registeredvisitor/edit password/**","registerform","/forgetPassword","/reset-pwd/",
                        "/resetPwdConfirm","/pwd-reset-success","/password/**","/forbidden-page","/error/**",
                        "/verification-link-failed","/about",
                        "/webjars/**","/css/**").permitAll()
                .antMatchers("/adminpage","/admin").access("hasAuthority('ADMIN')")
                .antMatchers("/writer").access("hasAuthority('WRITER')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/index");

    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}

