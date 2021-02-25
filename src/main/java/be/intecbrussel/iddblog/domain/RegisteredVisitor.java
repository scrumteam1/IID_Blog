package be.intecbrussel.iddblog.domain;

import be.intecbrussel.iddblog.validation.PasswordMatches;
import be.intecbrussel.iddblog.validation.ValidPassword;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//Spring security needs a table authorities with properties: username, authority
//Spring security needs a table users with properties: username, password, enabled
@Entity(name="RegisteredVisitor")
//Spring security needs a tables: authorities and users. Names of tables should be authorities and users.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@Table(name="users")
public class RegisteredVisitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "user name is mandatory.")
    @Size(min = 3, max = 20)
    private String username;

    @OneToOne(mappedBy = "registeredVisitor")
    private Authority authority;

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
    @Transient
    private String password;

    @Column(name = "password")
    private String encodedPassword;

    @NotNull
    @Size(min = 1)
    @Transient
    private String confirmPassword;

    @Transient
    private String oldPassword;

    private String gender;

    private Boolean isWriter;

}
