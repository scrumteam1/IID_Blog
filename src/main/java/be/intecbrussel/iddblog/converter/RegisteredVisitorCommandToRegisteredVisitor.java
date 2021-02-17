package be.intecbrussel.iddblog.converter;

import be.intecbrussel.iddblog.command.RegisteredVisitorCommand;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegisteredVisitorCommandToRegisteredVisitor implements Converter<RegisteredVisitorCommand, RegisteredVisitor> {

    public RegisteredVisitorCommandToRegisteredVisitor() {

    }


    @Synchronized
    @Nullable
    @Override
    public RegisteredVisitor convert(RegisteredVisitorCommand source){
        if(source == null){
            return null;
        }

        final RegisteredVisitor registeredVisitor = new RegisteredVisitor();
        registeredVisitor.setId(source.getId());
        registeredVisitor.setUsername(source.getUsername());
        registeredVisitor.setFirstName(source.getFirstName());
        registeredVisitor.setLastName(source.getLastName());
        registeredVisitor.setEmailAddress(source.getEmailAddress());
        registeredVisitor.setPassword(source.getPassword());
        registeredVisitor.setConfirmPassword(source.getConfirmPassword());
        registeredVisitor.setGender(source.getGender());
        registeredVisitor.setIsWriter(source.getIsWriter());


        return registeredVisitor;
    }
}
