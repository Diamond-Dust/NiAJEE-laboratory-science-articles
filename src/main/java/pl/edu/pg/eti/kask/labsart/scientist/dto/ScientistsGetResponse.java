package pl.edu.pg.eti.kask.labsart.scientist.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.net.URL;
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
public class ScientistsGetResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Scientist {
        private String login;
        private URL website;
    }

    @Singular
    private List<Scientist> scientists;

    public static Function<Collection<pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist>, ScientistsGetResponse> entityToDtoMapper() {
        return scientists -> {
            ScientistsGetResponse.ScientistsGetResponseBuilder response = ScientistsGetResponse.builder();
            scientists.stream()
                    .map(scientist -> Scientist.builder()
                            .login(scientist.getLogin())
                            .website(scientist.getWebsite())
                            .build())
                    .forEach(response::scientist);
            return response.build();
        };
    }
}
