package pl.edu.pg.eti.kask.labsart;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Article implements Serializable {
    private Long          id;
    private Publisher     publisher;
    private Scientist     author;
    private Set<Citation> sources;
    private String        title;

    //-----------------------------------------------

    //-----------------------------------------------

    public void addSource(Citation source) {
        sources.add(source);
    }

    public void removeSource(Citation source) {
        sources.remove(source);
    }

    //-----------------------------------------------

}
