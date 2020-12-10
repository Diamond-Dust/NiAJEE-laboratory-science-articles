package pl.edu.pg.eti.kask.labsart.article.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.model.ArticleModel;
import pl.edu.pg.eti.kask.labsart.article.model.ArticlesModel;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;
import pl.edu.pg.eti.kask.labsart.scientist.entity.UserRoles;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
public class ArticleView implements Serializable {

    /**
     * Service for managing articles.
     */
    private ArticleService articleService;
    private ScientistService scientistService;

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
    private ArticleModel article;

    public ArticleView() {}

    @Inject
    public void setService(ArticleService articleService, ScientistService scientistService) {
        this.articleService = articleService;
        this.scientistService = scientistService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Article> article;
        if(scientistService.isCaller(UserRoles.ADMIN)) {
            article = articleService.find(id);
        } else {
            article = articleService.findForCaller(id);
        }
        if (article.isPresent()) {
            this.article = ArticleModel.entityToModelMapper().apply(article.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Article not found");
        }
    }


    public String deleteAction(Citation citation) {
        articleService.deleteCitationForCaller(id, citation.getId());
        return "article_view?faces-redirect=true&includeViewParams=true";
    }
    public String strRet(Citation citation) {
        return citation.toString();
    }


}

