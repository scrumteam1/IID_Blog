package be.intecbrussel.iddblog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

//Spring security needs a table authorities with properties: username, authority
//Spring security needs a table users with properties: username, password, enabled
@Entity(name="Authority")
@DynamicUpdate
@Table(name="authorities", schema = "blog_dev")//Spring security needs a tables: authorities and users. Names of tables should be authorities and users.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "authority")
    @Fetch(FetchMode.SELECT)
    private List<RegisteredVisitor> registeredVisitors;


    @NotBlank(message = "Authority is mandatory")
    private String authority;
}
