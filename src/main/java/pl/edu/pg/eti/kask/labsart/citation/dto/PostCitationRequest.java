package pl.edu.pg.eti.kask.labsart.citation.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.dto.PostArticleRequest;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostCitationRequest {

    private String source;
    private int    pageNumber;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Citation, PostCitationRequest> entityToDtoMapper() {
        return citation -> PostCitationRequest.builder()
                .source(citation.getSource())
                .pageNumber(citation.getPageNumber())
                .build();
    }

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<PostCitationRequest, Citation> dtoToEntityMapper() {
        return postCitationRequest -> Citation.builder()
                .source(postCitationRequest.getSource())
                .pageNumber(postCitationRequest.getPageNumber())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Citation, PostCitationRequest, Citation> modelToEntityUpdater() {
        return (citation, request) -> {
            citation.setSource(request.getSource());
            citation.setPageNumber(request.getPageNumber());
            return citation;
        };
    }

}
