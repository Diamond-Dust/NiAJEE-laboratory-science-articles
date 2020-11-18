package pl.edu.pg.eti.kask.labsart.article.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ArticleModel {

    private Long id;

    private String title;

    @Singular
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<CitationModel> citationModels;

}
