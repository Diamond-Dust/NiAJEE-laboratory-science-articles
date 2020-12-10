package pl.edu.pg.eti.kask.labsart.citation.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationEditModel;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
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
    private CitationService citationService;
    private ArticleService articleService;

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

    public CitationEdit() {}

    @Inject
    public void setService(CitationService citationService, ArticleService articleService) {
        this.citationService = citationService;
        this.articleService = articleService;
    }

    private boolean updateModel() {
        Optional<Citation> citation = citationService.find(id);
        citation.ifPresent(value -> this.citation = CitationEditModel.entityToModelMapper().apply(value));
        return citation.isPresent();
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        if (!updateModel()) {
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
    public String saveAction() throws IOException {
        try {
            Citation cit = CitationEditModel.modelToEntityUpdater().apply(citationService.find(id).orElseThrow(), citation);
            citationService.updateArticleCitation(article, cit);
            updateModel();
            cit = CitationEditModel.modelToEntityUpdater().apply(citationService.find(id).orElseThrow(), citation);
            citationService.updateConnection(article, cit);
            String viewId = "/citation/citation_view";
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (EJBException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Version collision"));
            }
            return null;
        }

    }


}