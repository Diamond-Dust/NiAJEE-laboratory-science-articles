package pl.edu.pg.eti.kask.labsart.scientist.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.commontypes.Education;

import java.io.Serializable;
import java.net.URL;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Scientist implements Serializable {
    private String    login;
    private String    password;
    private double    hirschIndex;
    private URL       website;
    private Education education;
    private UUID      avatarId;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------
}
