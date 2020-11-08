package pl.edu.pg.eti.kask.labsart.citation.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.function.BiFunction;

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
    private Integer pageNumber;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------

    public static BiFunction<Citation, Citation, Citation> nonNullUpdateMapper() {
        return (toChange, newOne) -> Citation.builder()
                .id(newOne.getId() != null ? newOne.getId() : toChange.getId())
                .source(newOne.getSource() != null ? newOne.getSource() : toChange.getSource())
                .pageNumber(newOne.getPageNumber() != null ? newOne.getPageNumber() : toChange.getPageNumber())
                .build();
    }
}
