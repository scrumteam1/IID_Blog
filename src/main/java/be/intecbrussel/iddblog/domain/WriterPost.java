package be.intecbrussel.iddblog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


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

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

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
}
