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
public class PutPublisherRequest {
    private String         name;
    private Specialisation spec;
    private boolean        predatory;
    private double         impactFactor;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Publisher, PutPublisherRequest> entityToDtoMapper() {
        return publisher -> PutPublisherRequest.builder()
                .name(publisher.getName())
                .spec(publisher.getSpec())
                .predatory(publisher.getPredatory())
                .impactFactor(publisher.getImpactFactor())
                .build();
    }

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<PutPublisherRequest, Publisher> dtoToEntityMapper() {
        return putArticleRequest -> {
            Publisher publisher = Publisher.builder()
                    .name(putArticleRequest.getName())
                    .spec(putArticleRequest.getSpec())
                    .predatory(putArticleRequest.isPredatory())
                    .impactFactor(putArticleRequest.getImpactFactor())
                    .build();
            publisher.setArticles(null);
            return publisher;
        };
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Publisher, PutPublisherRequest, Publisher> modelToEntityUpdater() {
        return (publisher, request) -> {
            publisher.setName(request.getName());
            publisher.setImpactFactor(request.getImpactFactor());
            publisher.setSpec(request.getSpec());
            publisher.setPredatory(request.isPredatory());
            return publisher;
        };
    }
}
