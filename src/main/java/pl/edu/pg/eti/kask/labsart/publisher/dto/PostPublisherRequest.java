package pl.edu.pg.eti.kask.labsart.publisher.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostPublisherRequest {
    private String         name;
    private Specialisation spec;
    private boolean        predatory;
    private double         impactFactor;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Publisher, PostPublisherRequest> entityToDtoMapper() {
        return publisher -> PostPublisherRequest.builder()
                .name(publisher.getName())
                .spec(publisher.getSpec())
                .predatory(publisher.getPredatory())
                .impactFactor(publisher.getImpactFactor())
                .build();
    }

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<PostPublisherRequest, Publisher> dtoToEntityMapper() {
        return postPublisherRequest -> Publisher.builder()
                .name(postPublisherRequest.getName())
                .spec(postPublisherRequest.getSpec())
                .predatory(postPublisherRequest.isPredatory())
                .impactFactor(postPublisherRequest.getImpactFactor())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Publisher, PostPublisherRequest, Publisher> modelToEntityUpdater() {
        return (publisher, request) -> {
            publisher.setName(request.getName());
            publisher.setSpec(request.getSpec());
            publisher.setPredatory(request.isPredatory());
            publisher.setImpactFactor(request.getImpactFactor());
            return publisher;
        };
    }
}
