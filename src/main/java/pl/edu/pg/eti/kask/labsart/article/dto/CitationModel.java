package pl.edu.pg.eti.kask.labsart.article.dto;


import lombok.*;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class CitationModel {

    private Long    id;
    private String  source;
    private Integer pageNumber;

    public static Function<Collection<Citation>, Collection<CitationModel>> entitiesToDtosMapper() {
        return citations ->
                citations.stream()
                        .map(citation -> CitationModel.builder()
                                .id(citation.getId())
                                .source(citation.getSource())
                                .pageNumber(citation.getPageNumber())
                                .build())
                        .collect(Collectors.toList());
    }

}
