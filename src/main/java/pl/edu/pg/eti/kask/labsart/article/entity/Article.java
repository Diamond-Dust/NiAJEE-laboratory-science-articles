package pl.edu.pg.eti.kask.labsart.article.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.article.dto.PutArticleRequest;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Article implements Serializable {
    private Long          id;
    private String        title;
    private Publisher     publisher;
    private Scientist     author;
    @Builder.Default
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
