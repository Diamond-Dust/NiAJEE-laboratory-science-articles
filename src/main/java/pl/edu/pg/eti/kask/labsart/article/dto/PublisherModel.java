package pl.edu.pg.eti.kask.labsart.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class PublisherModel {
    private Long           id;

    private String         name;

    private Specialisation spec;

    private boolean        predatory;

    private double         impactFactor;

    public static Function<Publisher, PublisherModel> entityToDtoMapper() {
        return publisher -> PublisherModel.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .spec(publisher.getSpec())
                .predatory(publisher.isPredatory())
                .impactFactor(publisher.getImpactFactor())
                .build();
    }

}
