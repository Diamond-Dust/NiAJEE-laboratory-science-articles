package pl.edu.pg.eti.kask.labsart.citation.view;

import lombok.Getter;
import lombok.Setter;
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
import java.util.Optional;

@ViewScoped
@Named
public class CitationEdit implements Serializable {

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
    private CitationEditModel citation;

    @Inject
    public CitationEdit(CitationService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Citation> citation = service.find(id);
        if (citation.isPresent()) {
            this.citation = CitationEditModel.entityToModelMapper().apply(citation.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Citation not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        service.updateCitation(CitationEditModel.modelToEntityUpdater().apply(service.find(id).orElseThrow(), citation));
        String viewId = "/citation/citation_view"; //FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}