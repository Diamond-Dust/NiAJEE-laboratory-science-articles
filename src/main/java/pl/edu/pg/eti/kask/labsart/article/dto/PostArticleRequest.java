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
public class PostArticleRequest {

    private String               title;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Article, PostArticleRequest> entityToDtoMapper() {
        return article -> PostArticleRequest.builder()
                .title(article.getTitle())
                .build();
    }

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<PostArticleRequest, Article> dtoToEntityMapper() {
        return postArticleRequest -> Article.builder()
                .title(postArticleRequest.getTitle())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Article, PostArticleRequest, Article> modelToEntityUpdater() {
        return (article, request) -> {
            article.setTitle(request.getTitle());
            return article;
        };
    }

}
