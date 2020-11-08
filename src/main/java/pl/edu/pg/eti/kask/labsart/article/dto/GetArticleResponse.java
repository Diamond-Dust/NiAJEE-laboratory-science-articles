package pl.edu.pg.eti.kask.labsart.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.commontypes.Education;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistGetResponse;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetArticleResponse {

    private Long                 id;
    private String               title;
    private ScientistGetResponse author;
    private Publisher            publisher;
    @Singular
    private List<Citation>       citations;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Article, GetArticleResponse> entityToDtoMapper() {
        return character -> GetArticleResponse.builder()
                .id(character.getId())
                .title(character.getTitle())
                .author(character.getAuthor() != null ? ScientistGetResponse.entityToDtoMapper().apply(character.getAuthor()) : null)
                .publisher(character.getPublisher())
                .citations(character.getCitations())
                .build();
    }

}
