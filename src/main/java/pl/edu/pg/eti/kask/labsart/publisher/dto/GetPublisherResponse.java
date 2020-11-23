package pl.edu.pg.eti.kask.labsart.publisher.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;

import java.util.List;
import java.util.function.Function;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class GetPublisherResponse {
    private Long           id;
    private String         name;
    private Specialisation spec;
    private Boolean        predatory;
    private Double         impactFactor;

    @Singular
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<ArticleModel> articles;


    public static Function<Publisher, GetPublisherResponse> entityToDtoMapper() {
        return article -> GetPublisherResponse.builder()
                .id(article.getId())
                .name(article.getName())
                .spec(article.getSpec())
                .predatory(article.getPredatory())
                .impactFactor(article.getImpactFactor())
                .articles(ArticleModel.entitiesToDtosMapper().apply(article.getArticles()))
                .build();
    }
}
