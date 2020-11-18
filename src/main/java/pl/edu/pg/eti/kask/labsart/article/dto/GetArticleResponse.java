package pl.edu.pg.eti.kask.labsart.article.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistGetResponse;

import java.util.List;
import java.util.function.Function;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class GetArticleResponse {

    private Long                 id;
    private String               title;
    private ScientistGetResponse author;
    private PublisherModel       publisher;
    @Singular
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<CitationModel> citations;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Article, GetArticleResponse> entityToDtoMapper() {
        return article -> GetArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor() != null ? ScientistGetResponse.entityToDtoMapper().apply(article.getAuthor()) : null)
                .publisher(PublisherModel.entityToDtoMapper().apply(article.getPublisher()))
                .citations(CitationModel.entitiesToDtosMapper().apply(article.getCitations()))
                .build();
    }

}
