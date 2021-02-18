package be.intecbrussel.iddblog.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class RegisteredVisitorDetails implements UserDetails {

    private final RegisteredVisitor registeredVisitor;

    public RegisteredVisitorDetails(RegisteredVisitor registeredVisitor) {
        this.registeredVisitor = registeredVisitor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return registeredVisitor.getPassword();
    }

    @Override
    public String getUsername() {
        return registeredVisitor.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName(){
        return registeredVisitor.getFirstName() + " " + registeredVisitor.getLastName();
    }
}
