package pl.edu.pg.eti.kask.labsart.citation.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.model.ArticleModel;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationModel;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@RequestScoped
@Named
public class CitationView implements Serializable {

    /**
     * Service for managing articles.
     */
    private CitationService citationService;

    /**
     * Article id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Article exposed to the view.
     */
    @Getter
    private CitationModel citation;

    public CitationView() {}

    @Inject
    public void setService(CitationService citationService) {
        this.citationService = citationService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Citation> citation = citationService.find(id);
        if (citation.isPresent()) {
            this.citation = CitationModel.entityToModelMapper().apply(citation.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Citation not found");
        }
    }

    /**
     * Action for clicking delete action.
     *
     * @param citation citation to be removed
     * @return navigation case to list_characters
     */
    public String deleteAction(Citation citation) {
        citationService.delete(citation.getId());
        return "citation_view?faces-redirect=true";
    }


}
