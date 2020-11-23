package pl.edu.pg.eti.kask.labsart.publisher.dto;

import lombok.*;
import pl.edu.pg.eti.kask.labsart.article.dto.ArticleModel;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class GetPublishersResponse {
    @Singular
    private List<PublisherModel> publisherModels;

    public static Function<Collection<Publisher>, GetPublishersResponse> entityToDtoMapper() {
        return publishers -> {
            GetPublishersResponseBuilder response = GetPublishersResponse.builder();
            publishers.stream()
                    .map(publisher -> PublisherModel.entityToDtoMapper().apply(publisher))
                    .forEach(response::publisherModel);
            return response.build();
        };
    }
}
