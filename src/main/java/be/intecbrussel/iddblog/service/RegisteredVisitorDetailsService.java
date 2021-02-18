package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.RegisteredVisitorDetails;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RegisteredVisitorDetailsService implements UserDetailsService {

    @Autowired
    private RegisteredVisitorRepository registeredVisitorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        RegisteredVisitor rv = registeredVisitorRepository.findByUsername(username);
        if(rv == null){
            throw new UsernameNotFoundException("Username not found.");
        }
        return new RegisteredVisitorDetails(rv);
    }
}
