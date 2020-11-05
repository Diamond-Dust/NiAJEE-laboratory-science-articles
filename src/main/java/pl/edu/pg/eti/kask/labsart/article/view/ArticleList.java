package pl.edu.pg.eti.kask.labsart.article.view;

import pl.edu.pg.eti.kask.labsart.article.model.ArticlesModel;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class ArticleList implements Serializable {

    /**
     * Service for managing articles.
     */
    private final ArticleService service;

    /**
     * Articles list exposed to the view.
     */
    private  ArticlesModel articles;

    @Inject
    public ArticleList(ArticleService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all characters
     */
    public ArticlesModel getArticles() {
        if (articles == null) {
            articles = ArticlesModel.entityToModelMapper().apply(service.findAll());
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

}

