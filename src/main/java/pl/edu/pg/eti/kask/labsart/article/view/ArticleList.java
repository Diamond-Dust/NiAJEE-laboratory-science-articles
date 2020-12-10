package pl.edu.pg.eti.kask.labsart.article.view;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.model.ArticlesModel;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.scientist.entity.UserRoles;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class ArticleList implements Serializable {

    /**
     * Service for managing articles.
     */
    private ArticleService service;
    private ScientistService scientistService;

    /**
     * Articles list exposed to the view.
     */
    private  ArticlesModel articles;

    public ArticleList() {}

    /*@EJB
    public void setService(ArticleService service) {
        this.service = service;
    }

    @EJB
    public void setScientistService(ScientistService scientistService) {
        this.scientistService = scientistService;
    }*/
    @Inject
    public void setService(ArticleService service, ScientistService scientistService) {
        this.service = service;
        this.scientistService = scientistService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all characters
     */
    public ArticlesModel getArticles() {
        if (articles == null) {
            List<Article> articlesFromService;
            if(scientistService.isCaller(UserRoles.ADMIN)) {
                articlesFromService = service.findAll();
            } else {
                articlesFromService = service.findAllForCaller();
            }
            articles = ArticlesModel.entityToModelMapper().apply(articlesFromService);
        }
        return articles;
    }

    /**
     * Action for clicking delete action.
     *
     * @param article article to be removed
     * @return navigation case to list_characters
     */
    public String deleteAction(ArticlesModel.Article article) {
        service.delete(article.getId());
        return "article_list?faces-redirect=true";
    }

    public boolean canPrincipalDelete() {
        return scientistService.isCaller(UserRoles.ADMIN);
    }

}

