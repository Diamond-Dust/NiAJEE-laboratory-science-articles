package pl.edu.pg.eti.kask.labsart.article.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;


@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class GetArticlesResponse {

    /**
     * Name of the selected characters.
     */
    @Singular
    private List<ArticleModel> articleModels;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<pl.edu.pg.eti.kask.labsart.article.entity.Article>, GetArticlesResponse> entityToDtoMapper() {
        return articles -> {
            GetArticlesResponseBuilder response = GetArticlesResponse.builder();
            articles.stream()
                    .map(article -> ArticleModel.builder()
                            .id(article.getId())
                            .title(article.getTitle())
                            .citationModels(CitationModel.entitiesToDtosMapper().apply(article.getCitations()))
                            .build())
                    .forEach(response::articleModel);
            return response.build();
        };
    }

}

