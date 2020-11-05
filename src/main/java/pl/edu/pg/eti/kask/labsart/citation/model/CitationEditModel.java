package pl.edu.pg.eti.kask.labsart.citation.model;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CitationEditModel {

    private String source;
    private int    pageNumber;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Citation, CitationEditModel> entityToModelMapper() {
        return citation -> CitationEditModel.builder()
                .source(citation.getSource())
                .pageNumber(citation.getPageNumber())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Citation, pl.edu.pg.eti.kask.labsart.citation.model.CitationEditModel, Citation> modelToEntityUpdater() {
        return (citation, request) -> {
            citation.setPageNumber(request.getPageNumber());
            citation.setSource(request.getSource());
            return citation;
        };
    }

}
