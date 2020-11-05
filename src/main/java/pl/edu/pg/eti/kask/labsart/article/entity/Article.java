package pl.edu.pg.eti.kask.labsart.article.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Builder.Default
    private List<Citation> citations = new ArrayList<>();
    private String        title;

    //-----------------------------------------------

    //-----------------------------------------------

    //-----------------------------------------------

}
