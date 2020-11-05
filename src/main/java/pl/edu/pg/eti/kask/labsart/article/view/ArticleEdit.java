package pl.edu.pg.eti.kask.labsart.article.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.model.ArticleEditModel;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;

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
public class ArticleEdit implements Serializable {

    /**
     * Service for managing articles.
     */
    private final ArticleService service;

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
    private ArticleEditModel article;

    @Inject
    public ArticleEdit(ArticleService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Article> article = service.find(id);
        if (article.isPresent()) {
            this.article = ArticleEditModel.entityToModelMapper().apply(article.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Article not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        service.update(ArticleEditModel.modelToEntityUpdater().apply(service.find(id).orElseThrow(), article));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}

