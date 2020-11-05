package pl.edu.pg.eti.kask.labsart.article.model;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ArticleModel {

    private Long           id;
    private Publisher      publisher;
    private Scientist      author;
    @Singular
    private List<Citation> citations;
    private String         title;


    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Article, ArticleModel> entityToModelMapper() {
        return article -> {
            return ArticleModel.builder()
                .id(article.getId())
                .publisher(article.getPublisher())
                .author(article.getAuthor())
                .title(article.getTitle())
                .citations(article.getCitations())
                .build();
        };
    }

}

