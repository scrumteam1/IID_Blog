package be.intecbrussel.iddblog.domain;

import be.intecbrussel.iddblog.validation.PasswordConstraintValidator;
import be.intecbrussel.iddblog.validation.ValidPassword;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Constraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredVisitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "user name is mandatory.")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "first name is mandatory.")
    @Size(min = 1, max = 20)
    private String firstName;

    @NotBlank(message = "last name is mandatory.")
    @Size(min = 1, max = 20)
    private String lastName;

    @NotBlank(message = "email is mandatory.")
    @Email(message = "email should be valid.")
    private String emailAddress;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

    private String gender;

    private Boolean isWriter;

}
