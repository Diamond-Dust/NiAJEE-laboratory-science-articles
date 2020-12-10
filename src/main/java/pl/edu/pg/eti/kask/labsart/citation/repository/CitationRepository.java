package pl.edu.pg.eti.kask.labsart.citation.repository;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
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
public class CitationRepository  implements Repository<Citation, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    //-----------------------------------------------

    //-----------------------------------------------

    @Override
    public Optional<Citation> find(Long id) {
        return Optional.ofNullable(em.find(Citation.class, id));
    }

    @Override
    public List<Citation> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Citation> query = cb.createQuery(Citation.class);
        Root<Citation> root = query.from(Citation.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Citation entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Citation entity) {
        em.remove(em.find(Citation.class, entity.getId()));
    }

    @Override
    public void update(Citation entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Citation entity) {
        em.detach(entity);
    }

    //-----------------------------------------------
    public Optional<Citation> findForArticle(Long id, Long articleId) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Citation> query = cb.createQuery(Citation.class);
            Root<Citation> root = query.from(Citation.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Citation_.article), articleId),
                            cb.equal(root.get(Citation_.id), id)));
            return Optional.of(em.createQuery(query).getSingleResult());

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
