package pl.edu.pg.eti.kask.labsart.article.repository;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.repository.Repository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Dependent
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

    //--------------------------------------------

    public Optional<Article> findForScientist(Long id, Scientist scientist) {
        try {
            return Optional.of(
                    em.createQuery("select a from Article a where a.author = :author and a.id = :id", Article.class)
                        .setParameter("author", scientist)
                        .setParameter("id", id)
                        .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Article> findAllForScientist(Scientist scientist) {
        return em.createQuery("select a from Article a where a.author = :author", Article.class)
                .setParameter("author", scientist)
                .getResultList();
    }

}
