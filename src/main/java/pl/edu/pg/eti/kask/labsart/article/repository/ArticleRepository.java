package pl.edu.pg.eti.kask.labsart.article.repository;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.entity.Article_;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation_;
import pl.edu.pg.eti.kask.labsart.repository.Repository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> query = cb.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Article> query = cb.createQuery(Article.class);
            Root<Article> root = query.from(Article.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Article_.author), scientist),
                            cb.equal(root.get(Article_.id), id)));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Article> findAllForScientist(Scientist scientist) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> query = cb.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);
        query.select(root)
                .where(
                        cb.equal(
                                root.get(Article_.author),
                                scientist
                        )
                );
        return em.createQuery(query).getResultList();
    }

}
