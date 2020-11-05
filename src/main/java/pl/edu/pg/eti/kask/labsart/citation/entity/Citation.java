package pl.edu.pg.eti.kask.labsart.citation.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Citation implements Serializable {
    private Long   id;
    private String source;
    private int    pageNumber;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------
}
