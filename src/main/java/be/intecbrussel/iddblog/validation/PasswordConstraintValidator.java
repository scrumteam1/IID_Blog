package be.intecbrussel.iddblog.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import java.util.Arrays;

import com.google.common.base.Joiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        // @formatter:off
        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                // define some illegal sequences that will fail when >= 3 chars long
                // alphabetical is of the form 'abc', numerical is '345'
                // the false parameter indicates that wrapped sequences are allowed; e.g.
                // 'xyzabc'
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
                new WhitespaceRule()));
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }
}
