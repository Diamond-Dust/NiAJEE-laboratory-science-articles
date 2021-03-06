package pl.edu.pg.eti.kask.labsart.article.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "articles")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long          id;

    private String        title;

    @ManyToOne
    @JoinColumn(name ="publisher")
    @JsonBackReference
    private Publisher     publisher;

    @ManyToOne
    @JoinColumn(name ="scientist")
    @JsonBackReference
    private Scientist     author;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    @Basic(fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Citation> citations = new ArrayList<>();

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------

    public static BiFunction<Article, Article, Article> nonNullUpdateMapper() {
        return (toChange, newOne) -> Article.builder()
                .id(newOne.getId() != null ? newOne.getId() : toChange.getId())
                .title(newOne.getTitle() != null ? newOne.getTitle() : toChange.getTitle())
                .publisher(newOne.getPublisher() != null ? newOne.getPublisher() : toChange.getPublisher())
                .author(newOne.getAuthor() != null ? newOne.getAuthor() : toChange.getAuthor())
                .citations(newOne.getCitations() != null ? newOne.getCitations() : toChange.getCitations())
                .build();
    }
}
