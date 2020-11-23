package pl.edu.pg.eti.kask.labsart.publisher.dto;


import lombok.*;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;

import java.util.function.Function;

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
                .predatory(publisher.getPredatory())
                .impactFactor(publisher.getImpactFactor())
                .build();
    }

}
