package pl.edu.pg.eti.kask.labsart.publisher.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "publishers")
public class Publisher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long           id;

    private String         name;

    private Specialisation spec;

    private Boolean        predatory;

    private Double         impactFactor;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Article> articles = new ArrayList<>();

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------

    public static BiFunction<Publisher, Publisher, Publisher> nonNullUpdateMapper() {
        return (toChange, newOne) -> Publisher.builder()
                .id(newOne.getId() != null ? newOne.getId() : toChange.getId())
                .name(newOne.getName() != null ? newOne.getName() : toChange.getName())
                .spec(newOne.getSpec() != null ? newOne.getSpec() : toChange.getSpec())
                .predatory(newOne.getPredatory() != null ? newOne.getPredatory() : toChange.getPredatory())
                .impactFactor(newOne.getImpactFactor() != null ? newOne.getImpactFactor() : toChange.getImpactFactor())
                .articles(newOne.getArticles() != null ? newOne.getArticles() : toChange.getArticles())
                .build();
    }
}
