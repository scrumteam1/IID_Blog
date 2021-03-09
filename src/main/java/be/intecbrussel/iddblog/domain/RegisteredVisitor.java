package be.intecbrussel.iddblog.domain;

import be.intecbrussel.iddblog.validation.PasswordMatches;
import be.intecbrussel.iddblog.validation.ValidPassword;
import lombok.*;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
public class RegisteredVisitor implements Diffable<RegisteredVisitor> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "user name is mandatory.")
    @Size(min = 3, max = 20)
    private String username;

    @OneToOne(mappedBy = "registeredVisitor")
    private Authority authority;

    @OneToMany(mappedBy = "registeredVisitor",cascade = CascadeType.ALL)
    private List<VerificationToken> verificationToken;

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

    private String gender = "Unknown";

    private Boolean isWriter;

    @Column(nullable = true)
    @Lob
    private String avatar;

    @Column(name = "enabled")
    private boolean enabled = false;

    @Override
    public DiffResult<RegisteredVisitor> diff(RegisteredVisitor registeredVisitor) {
        return new DiffBuilder(this, registeredVisitor, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", this.id, registeredVisitor.id)
                .append("username", this.username,registeredVisitor.username)
                .append("first name", this.firstName,registeredVisitor.firstName)
                .append("last name", this.lastName,registeredVisitor.lastName)
                .append("email address", this.emailAddress,registeredVisitor.emailAddress)
                .append("gender", this.gender,registeredVisitor.gender)
                .append("iswriter", this.isWriter, registeredVisitor.isWriter)
                .append("avatar", this.avatar,registeredVisitor.avatar)
                .append("enabled", this.enabled,registeredVisitor.enabled)
                .build();
    }
}
