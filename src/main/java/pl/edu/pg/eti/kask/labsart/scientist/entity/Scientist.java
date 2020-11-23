package pl.edu.pg.eti.kask.labsart.scientist.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.commontypes.Education;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

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

    private Double hirschIndex;

    private URL       website;

    private Education education;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Article> articles = new ArrayList<>();

    @OneToOne
    private Avatar avatar;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------

    public static BiFunction<Scientist, Scientist, Scientist> nonNullUpdateMapper() {
        return (toChange, newOne) -> Scientist.builder()
                .login(newOne.getLogin() != null ? newOne.getLogin() : toChange.getLogin())
                .password(newOne.getPassword() != null ? newOne.getPassword() : toChange.getPassword())
                .hirschIndex(newOne.getHirschIndex() != null ? newOne.getHirschIndex() : toChange.getHirschIndex())
                .website(newOne.getWebsite() != null ? newOne.getWebsite() : toChange.getWebsite())
                .education(newOne.getEducation() != null ? newOne.getEducation() : toChange.getEducation())
                .articles(newOne.getArticles() != null ? newOne.getArticles() : toChange.getArticles())
                .avatar(newOne.getAvatar() != null ? newOne.getAvatar() : toChange.getAvatar())
                .build();
    }
}
