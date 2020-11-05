package pl.edu.pg.eti.kask.labsart.citation.model;

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
public class CitationCreateModel {

    private String source;
    private int    pageNumber;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Citation, CitationCreateModel> entityToModelMapper() {
        return citation -> CitationCreateModel.builder()
                .source(citation.getSource())
                .pageNumber(citation.getPageNumber())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Long, pl.edu.pg.eti.kask.labsart.citation.model.CitationCreateModel, Citation> modelToEntityUpdater() {
        return (id, request) -> Citation.builder()
                .id(id)
                .pageNumber(request.getPageNumber())
                .source(request.getSource())
                .build();
    }

}
