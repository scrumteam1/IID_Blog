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
        //disable security to not asking to login at this time (not needed for signup)
        //security.httpBasic().disable();

        security
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/adminpage").access("hasAuthority('ADMIN')")
                .antMatchers("/", "/index", "/registeredvisitor/**","/registerform/**","/login",
                        "registeredvisitor/edit password/**","registerform","/forgetPassword","/reset-pwd/",
                        "/resetPwdConfirm","/pwd-reset-success",
                        "/webjars/**","/css/**").permitAll()
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

