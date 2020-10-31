package pl.edu.pg.eti.kask.labsart;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Publisher implements Serializable {
    private Long           id;
    private String         name;
    private Specialisation spec;
    private boolean        predatory;
    private double         impactFactor;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------
}
