package pl.edu.pg.eti.kask.labsart.article.model;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ArticleEditModel {

    private Publisher     publisher;
    private Scientist     author;
    private List<Citation> citations;
    private String        title;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Article, ArticleEditModel> entityToModelMapper() {
        return character -> ArticleEditModel.builder()
                .publisher(character.getPublisher())
                .author(character.getAuthor())
                .title(character.getTitle())
                .citations(character.getCitations())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Article, ArticleEditModel, Article> modelToEntityUpdater() {
        return (article, request) -> {
            article.setPublisher(request.getPublisher());
            article.setAuthor(request.getAuthor());
            article.setTitle(request.getTitle());
            article.setCitations(request.getCitations());
            return article;
        };
    }

}

