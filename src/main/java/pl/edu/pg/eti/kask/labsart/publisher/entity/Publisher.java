package pl.edu.pg.eti.kask.labsart.publisher.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private Long           id;

    private String         name;

    private Specialisation spec;

    private boolean        predatory;

    private double         impactFactor;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    private List<Article> articles = new ArrayList<>();

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------
}
