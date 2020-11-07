package pl.edu.pg.eti.kask.labsart.citation.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationEditModel;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ViewScoped
@Named
public class CitationEdit implements Serializable {

    /**
     * Citation for managing articles.
     */
    private final CitationService citationService;
    private final ArticleService articleService;

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
    private CitationEditModel citation;

    @Inject
    public CitationEdit(CitationService citationService, ArticleService articleService) {
        this.citationService = citationService;
        this.articleService = articleService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Citation> citation = citationService.find(id);
        if (citation.isPresent()) {
            this.citation = CitationEditModel.entityToModelMapper().apply(citation.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Citation not found");
        }
        articles = articleService.findAll();
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        citationService.updateArticleCitation(article, CitationEditModel.modelToEntityUpdater().apply(citationService.find(id).orElseThrow(), citation));
        String viewId = "/citation/citation_view"; //FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}