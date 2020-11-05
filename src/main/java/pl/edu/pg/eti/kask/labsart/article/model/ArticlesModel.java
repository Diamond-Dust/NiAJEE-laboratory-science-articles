package pl.edu.pg.eti.kask.labsart.article.model;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ArticlesModel implements Serializable {

    /**
     * Represents single article in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Article {

        private Long id;
        private String title;

    }

    /**
     * Name of the selected articles.
     */
    @Singular
    private List<Article> articles;

    /**
     * @return mapper for convenient converting entity object to model object
     */
    public static Function<Collection<pl.edu.pg.eti.kask.labsart.article.entity.Article>, ArticlesModel> entityToModelMapper() {
        return articles -> {
            ArticlesModel.ArticlesModelBuilder model = ArticlesModel.builder();
            articles.stream()
                    .map(article -> ArticlesModel.Article.builder()
                            .id(article.getId())
                            .title(article.getTitle())
                            .build())
                    .forEach(model::article);
            return model.build();
        };
    }

}

