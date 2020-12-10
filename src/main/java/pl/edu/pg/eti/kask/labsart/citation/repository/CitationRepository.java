package pl.edu.pg.eti.kask.labsart.citation.repository;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
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
        return em.createQuery("select c from Citation c", Citation.class).getResultList();
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
            return Optional.of(
                    em.createQuery("select c from Citation c where c.article = :article and c.id = :id", Citation.class)
                            .setParameter("article", articleId)
                            .setParameter("id", id)
                            .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
