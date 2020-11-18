package pl.edu.pg.eti.kask.labsart.article.repository;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestScoped
public class ArticleRepository  implements Repository<Article, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    //-----------------------------------------------

    //-----------------------------------------------

    @Override
    public Optional<Article> find(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class).getResultList();
    }

    @Override
    public void create(Article entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Article entity) {
        em.remove(em.find(Article.class, entity.getId()));
    }

    @Override
    public void update(Article entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Article entity) {
        em.detach(entity);
    }

}
