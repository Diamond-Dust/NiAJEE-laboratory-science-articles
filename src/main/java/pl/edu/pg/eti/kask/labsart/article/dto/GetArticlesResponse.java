package pl.edu.pg.eti.kask.labsart.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;

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
public class GetArticlesResponse {

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
        @Singular
        private List<Citation> citations;

    }

    /**
     * Name of the selected characters.
     */
    @Singular
    private List<Article> articles;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<pl.edu.pg.eti.kask.labsart.article.entity.Article>, GetArticlesResponse> entityToDtoMapper() {
        return articles -> {
            GetArticlesResponseBuilder response = GetArticlesResponse.builder();
            articles.stream()
                    .map(article -> Article.builder()
                            .id(article.getId())
                            .title(article.getTitle())
                            .citations(article.getCitations())
                            .build())
                    .forEach(response::article);
            return response.build();
        };
    }

}

