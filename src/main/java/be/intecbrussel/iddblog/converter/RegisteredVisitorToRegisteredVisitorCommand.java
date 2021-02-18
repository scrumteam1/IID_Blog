package be.intecbrussel.iddblog.converter;

import be.intecbrussel.iddblog.command.RegisteredVisitorCommand;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegisteredVisitorToRegisteredVisitorCommand implements Converter<RegisteredVisitor, RegisteredVisitorCommand> {


    public RegisteredVisitorToRegisteredVisitorCommand() {
    }


    @Synchronized
    @Nullable
    @Override
    public RegisteredVisitorCommand convert(RegisteredVisitor source){
        if(source == null){
            return null;
        }

        final RegisteredVisitorCommand command = new RegisteredVisitorCommand();
        command.setId(source.getId());
        command.setUsername(source.getUsername());
        command.setFirstName(source.getFirstName());
        command.setLastName(source.getLastName());
        command.setEmailAddress(source.getEmailAddress());
        command.setPassword(source.getPassword());
        command.setConfirmPassword(source.getConfirmPassword());
        command.setGender(source.getGender());
        command.setIsWriter(source.getIsWriter());

        return command;
    }
}
