package be.intecbrussel.iddblog.validation;


import be.intecbrussel.iddblog.domain.RegisteredVisitor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<be.intecbrussel.iddblog.validation.PasswordMatches, Object> {
    private String message;

    @Override
    public void initialize(final be.intecbrussel.iddblog.validation.PasswordMatches constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RegisteredVisitor user = (RegisteredVisitor) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }

}
