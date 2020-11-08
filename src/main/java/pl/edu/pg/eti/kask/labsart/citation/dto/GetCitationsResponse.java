package pl.edu.pg.eti.kask.labsart.citation.dto;

import lombok.*;

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
public class GetCitationsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Citation {

        private Long   id;
        private String source;
        private int    pageNumber;

    }

    /**
     * Name of the selected characters.
     */
    @Singular
    private List<Citation> citations;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<pl.edu.pg.eti.kask.labsart.citation.entity.Citation>, GetCitationsResponse> entityToDtoMapper() {
        return citations -> {
            GetCitationsResponse.GetCitationsResponseBuilder response = GetCitationsResponse.builder();
            citations.stream()
                    .map(citation -> Citation.builder()
                            .id(citation.getId())
                            .source(citation.getSource())
                            .pageNumber(citation.getPageNumber())
                            .build())
                    .forEach(response::citation);
            return response.build();
        };
    }

}
