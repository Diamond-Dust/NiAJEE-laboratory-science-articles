package pl.edu.pg.eti.kask.labsart.scientist.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ScientistPutRequest {

    private String password;

    public static Function<Scientist, ScientistPutRequest> entityToDtoMapper() {
        return scientist -> ScientistPutRequest.builder()
                .password(scientist.getPassword())
                .build();
    }

    public static Function<ScientistPutRequest, Scientist> dtoToEntityMapper() {
        return putArticleRequest -> Scientist.builder()
                .password(putArticleRequest.getPassword())
                .build();
    }

    public static BiFunction<Scientist, ScientistPutRequest, Scientist> modelToEntityUpdater() {
        return (scientist, request) -> {
            scientist.setPassword(request.getPassword());
            return scientist;
        };
    }
}
