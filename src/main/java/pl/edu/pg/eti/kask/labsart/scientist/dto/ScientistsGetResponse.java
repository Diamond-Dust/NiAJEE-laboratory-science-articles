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
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ScientistsGetResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class ListScientist {
        private String login;
        private String website;
    }

    @Singular
    private List<ListScientist> scientists;

    public static Function<Collection<pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist>, ScientistsGetResponse> entityToDtoMapper() {
        return scientists -> {
            ScientistsGetResponse.ScientistsGetResponseBuilder response = ScientistsGetResponse.builder();
            scientists.stream()
                    .map(scientist -> ListScientist.builder()
                            .login(scientist.getLogin())
                            .website((scientist.getWebsite() == null) ? "null" : scientist.getWebsite().toString())
                            .build()
                    )
                    .forEach(response::scientist);
            return response.build();
        };
    }

    public String getScientistsString() {
        String res = "[\n";
        for(int i=0; i<scientists.size(); i++)
        {
            ListScientist s = scientists.get(i);
            res += (null == s) ? "NULL\n" : ((null == s.login) ?"null":s.login) + " " + ((null == s.website) ? "null" : s.website.toString()) + '\n';
        }
        res += ']';

        return res;
    }
}
