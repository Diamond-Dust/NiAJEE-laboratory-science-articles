package pl.edu.pg.eti.kask.labsart.publisher.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
class ArticleModel {

    private Long id;
    private String title;

    @Singular
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<CitationModel> citationModels;

    static Function<Collection<Article>, Collection<ArticleModel>> entitiesToDtosMapper() {
        return articles ->
                articles.stream()
                        .map(article -> ArticleModel.builder()
                                .id(article.getId())
                                .title(article.getTitle())
                                .citationModels(CitationModel.entitiesToDtosMapper().apply(article.getCitations()))
                                .build())
                        .collect(Collectors.toList());
    }

}

