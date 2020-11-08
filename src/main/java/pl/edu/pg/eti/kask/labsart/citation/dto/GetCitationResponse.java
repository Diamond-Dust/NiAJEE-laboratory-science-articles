package pl.edu.pg.eti.kask.labsart.citation.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistGetResponse;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCitationResponse {

    private Long                 id;
    private String               source;
    private int                  pageNumber;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Citation, GetCitationResponse> entityToDtoMapper() {
        return citation -> GetCitationResponse.builder()
                .id(citation.getId())
                .source(citation.getSource())
                .pageNumber(citation.getPageNumber())
                .build();
    }

}
