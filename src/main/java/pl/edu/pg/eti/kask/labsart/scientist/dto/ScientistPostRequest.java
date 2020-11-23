package pl.edu.pg.eti.kask.labsart.scientist.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.commontypes.Education;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.net.URL;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ScientistPostRequest {
    private String login;
    private String password;
    private double hirschIndex;

    public static Function<Scientist, ScientistPostRequest> entityToDtoMapper() {
        return scientist -> ScientistPostRequest.builder()
                .login(scientist.getLogin())
                .password(scientist.getPassword())
                .hirschIndex(scientist.getHirschIndex())
                .build();
    }

    public static Function<ScientistPostRequest, Scientist> dtoToEntityMapper() {
        return scientistPostRequest -> Scientist.builder()
                .login(scientistPostRequest.getLogin())
                .password(scientistPostRequest.getPassword())
                .hirschIndex(scientistPostRequest.getHirschIndex())
                .build();
    }

    public static BiFunction<Scientist, ScientistPostRequest, Scientist> modelToEntityUpdater() {
        return (scientist, request) -> {
            scientist.setLogin(request.getLogin());
            scientist.setPassword(request.getPassword());
            scientist.setHirschIndex(request.getHirschIndex());
            return scientist;
        };
    }

}
