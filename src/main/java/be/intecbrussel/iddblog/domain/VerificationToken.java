package be.intecbrussel.iddblog.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "VerificationToken")
@Table(name = "verification_token")
@Data
@DynamicUpdate
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String token;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private RegisteredVisitor registeredVisitor;

    private Date expiryDate;

    public VerificationToken() {

    }

    public VerificationToken(final String token) {

        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    public VerificationToken(final String token, final RegisteredVisitor visitor) {

        this.token = token;
        this.registeredVisitor = visitor;
        this.userId = visitor.getId();
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExpiryDate() == null) ? 0 : getExpiryDate().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getRegisteredVisitor() == null) ? 0 : getRegisteredVisitor().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VerificationToken other = (VerificationToken) obj;
        if (getExpiryDate() == null) {
            if (other.getExpiryDate() != null) {
                return false;
            }
        } else if (!getExpiryDate().equals(other.getExpiryDate())) {
            return false;
        }
        if (getToken() == null) {
            if (other.getToken() != null) {
                return false;
            }
        } else if (!getToken().equals(other.getToken())) {
            return false;
        }
        if (getRegisteredVisitor() == null) {
            return other.getRegisteredVisitor() == null;
        } else return getRegisteredVisitor().equals(other.getRegisteredVisitor());
    }

    @Override
    public String toString() {
        return "Token [String=" + token + "]" + "[Expires" + expiryDate + "]";
    }
}