package pl.edu.pg.eti.kask.labsart.citation.dto;

import lombok.*;
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
public class PutCitationRequest {

    private String source;
    private Integer pageNumber;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Citation, PutCitationRequest> entityToDtoMapper() {
        return citation -> PutCitationRequest.builder()
                .source(citation.getSource())
                .pageNumber(citation.getPageNumber())
                .build();
    }

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<PutCitationRequest, Citation> dtoToEntityMapper() {
        return putArticleRequest -> Citation.builder()
                .source(putArticleRequest.getSource())
                .pageNumber(putArticleRequest.getPageNumber())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Citation, PutCitationRequest, Citation> modelToEntityUpdater() {
        return (citation, request) -> {
            citation.setSource(request.getSource());
            citation.setPageNumber(request.getPageNumber());
            return citation;
        };
    }

}
