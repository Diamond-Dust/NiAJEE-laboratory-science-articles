package pl.edu.pg.eti.kask.labsart.scientist.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.commontypes.Education;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "scientists")
public class Scientist implements Serializable {
    @Id
    private String    login;

    @ToString.Exclude
    private String    password;

    private double    hirschIndex;

    private URL       website;

    private Education education;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "scientist", cascade = CascadeType.REMOVE)
    private List<Article> articles = new ArrayList<>();

    @OneToOne
    private UUID      avatarId;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------
}
