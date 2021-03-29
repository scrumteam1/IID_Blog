package be.intecbrussel.iddblog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.Period;

@Entity(name = "comment")
@Table(name = "comments")
@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;

    @NotBlank(message = "Type your comment")
    @Column(name = "content")
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private RegisteredVisitor registeredVisitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private WriterPost writerPost;

    public int getDaysAgo(LocalDate creationDate){
        LocalDate today = LocalDate.now();
        return Period.between(today, creationDate).getDays();
    }
}
