package be.intecbrussel.iddblog.security;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private RegisteredVisitorRepository userRepository;

    public MyUserDetailsService(RegisteredVisitorRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        RegisteredVisitor user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        MyUserDetails myUserDetails = new MyUserDetails(user);
        return myUserDetails;
    }
}
