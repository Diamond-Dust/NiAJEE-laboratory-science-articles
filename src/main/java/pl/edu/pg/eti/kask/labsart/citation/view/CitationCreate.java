package pl.edu.pg.eti.kask.labsart.citation.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationCreateModel;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationEditModel;
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
    private CitationService citationService;
    private ArticleService  articleService;

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

    public CitationCreate() {}

    @Inject
    public void setService(CitationService citationService, ArticleService articleService) {
        this.citationService = citationService;
        this.articleService = articleService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        citation = new CitationCreateModel();
        articles = articleService.findAll();
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        Citation cit = CitationCreateModel.modelToEntityUpdater().apply(id, citation);
        citationService.createCitation(article.getId(), cit);
        String viewId = "/citation/citation_view?id=" + id; //FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "&faces-redirect=true&includeViewParams=true";
    }

}