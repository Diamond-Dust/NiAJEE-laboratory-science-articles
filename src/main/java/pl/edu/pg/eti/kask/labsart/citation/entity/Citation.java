package pl.edu.pg.eti.kask.labsart.citation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "citations")
public class Citation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long    id;

    private String  source;

    private Integer pageNumber;

    @ManyToOne
    @JoinColumn(name ="article")
    @JsonBackReference
    private Article article;


    @Version
    private Long version;


    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;

    @PrePersist
    public void updateCreationDateTime() {
        creationDateTime = LocalDateTime.now();
    }
    @PreUpdate
    public void updateUpdateDateTime() {
        updateDateTime = LocalDateTime.now();
    }


    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------

    public static BiFunction<Citation, Citation, Citation> nonNullUpdateMapper() {
        return (toChange, newOne) -> Citation.builder()
                .id(newOne.getId() != null ? newOne.getId() : toChange.getId())
                .source(newOne.getSource() != null ? newOne.getSource() : toChange.getSource())
                .pageNumber(newOne.getPageNumber() != null ? newOne.getPageNumber() : toChange.getPageNumber())
                .build();
    }
}
