package pl.edu.pg.eti.kask.labsart.scientist.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.Education;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.net.URL;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ScientistGetResponse {
    private String    login;
    private double    hirschIndex;
    private URL       website;
    private Education education;

    //-----------------------------------------------

    //-----------------------------------------------

    public static Function<Scientist, ScientistGetResponse> entityToDtoMapper() {
        return scientist -> ScientistGetResponse.builder()
                .login(scientist.getLogin())
                .hirschIndex(scientist.getHirschIndex())
                .website(scientist.getWebsite())
                .education(scientist.getEducation())
                .build();
    }

}
