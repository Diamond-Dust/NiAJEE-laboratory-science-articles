package pl.edu.pg.eti.kask.labsart.citation.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationCreateModel;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationEditModel;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
public class CitationCreate implements Serializable {

    /**
     * Citation for managing articles.
     */
    private final CitationService service;

    /**
     * Citation id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Citation exposed to the view.
     */
    @Getter
    private CitationCreateModel citation;

    @Inject
    public CitationCreate(CitationService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        id = service.getNewId();
        citation = new CitationCreateModel();
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        service.createCitation(7641L, CitationCreateModel.modelToEntityUpdater().apply(id, citation));
        String viewId = "/citation/citation_view?id=" + id; //FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "&faces-redirect=true&includeViewParams=true";
    }

}