package pl.edu.pg.eti.kask.labsart.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutArticleRequest {

    private String               title;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Article, PutArticleRequest> entityToDtoMapper() {
        return article -> PutArticleRequest.builder()
                .title(article.getTitle())
                .build();
    }

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<PutArticleRequest, Article> dtoToEntityMapper() {
        return putArticleRequest -> {
            Article article = Article.builder()
                .title(putArticleRequest.getTitle())
                .build();
            article.setCitations(null);
            return article;
        };
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Article, PutArticleRequest, Article> modelToEntityUpdater() {
        return (article, request) -> {
            article.setTitle(request.getTitle());
            return article;
        };
    }

}
