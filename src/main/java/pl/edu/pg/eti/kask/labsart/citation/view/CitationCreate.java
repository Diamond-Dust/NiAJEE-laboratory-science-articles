package pl.edu.pg.eti.kask.labsart.citation.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationCreateModel;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class CitationCreate implements Serializable {

    /**
     * Citation for managing articles.
     */
    private final CitationService citationService;
    private final ArticleService  articleService;

    /**
     * Citation id.
     */
    @Setter
    @Getter
    private Long id;
    @Setter
    @Getter
    private List<Article> articles;
    @Setter
    @Getter
    private Article article;

    /**
     * Citation exposed to the view.
     */
    @Getter
    private CitationCreateModel citation;

    @Inject
    public CitationCreate(CitationService citationService, ArticleService articleService) {
        this.citationService = citationService;
        this.articleService = articleService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        id = citationService.getNewId();
        citation = new CitationCreateModel();
        articles = articleService.findAll();
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        citationService.createCitation(article.getId(), CitationCreateModel.modelToEntityUpdater().apply(id, citation));
        String viewId = "/citation/citation_view?id=" + id; //FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "&faces-redirect=true&includeViewParams=true";
    }

}