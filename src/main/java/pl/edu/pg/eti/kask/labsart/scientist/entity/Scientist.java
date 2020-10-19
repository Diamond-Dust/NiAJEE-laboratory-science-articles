package pl.edu.pg.eti.kask.labsart.scientist.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.Education;

import java.io.Serializable;
import java.net.URL;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Scientist implements Serializable {
    private String    login;
    private String    password;
    private double    hirschIndex;
    private URL       website;
    private Education education;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------
}
