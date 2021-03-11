package be.intecbrussel.iddblog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "WriterPost")
@Table(name = "writerposts")
@Data
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class WriterPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Creation_date")
    private Date creationDate;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    @Lob
    private String content;

    @Column (name = "status")
    private String status;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private RegisteredVisitor registeredVisitor;

}
