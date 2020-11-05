package pl.edu.pg.eti.kask.labsart.article.repository;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ArticleRepository  implements Repository<Article, Long> {
    private DataStore store;

    //-----------------------------------------------

    @Inject
    public ArticleRepository(DataStore store) {
        this.store = store;
    }

    //-----------------------------------------------

    @Override
    public Optional<Article> find(Long id) {
        return store.findArticle(id);
    }

    @Override
    public List<Article> findAll() {
        return store.findAllArticles();
    }

    @Override
    public void create(Article entity) {
        store.createArticle(entity);
    }

    @Override
    public void delete(Article entity) {
        store.deleteArticle(entity);
    }

    @Override
    public void update(Article entity) {
        store.updateArticle(entity);
    }
}
