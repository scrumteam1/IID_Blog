package be.intecbrussel.iddblog.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "WriterPost")
@Table(name = "writerposts")
@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class WriterPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate;

    @Column(name = "title")
    private String title;

    @NotBlank(message = "An introduction is mandatory!")
    @Column (name = "intro")
    @Lob
    private String intro;

    @Column(name = "content")
    @Lob
    private String content;

    @Column (name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private RegisteredVisitor registeredVisitor;

    private Boolean isEnabled = true;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name="writerposts_tags",
            joinColumns = {@JoinColumn(name="POST_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="TAG_ID", referencedColumnName = "ID")}
    )
    private Set<Tag> tags = new HashSet<>();
}
