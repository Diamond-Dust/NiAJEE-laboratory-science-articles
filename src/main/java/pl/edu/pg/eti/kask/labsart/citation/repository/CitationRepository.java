package pl.edu.pg.eti.kask.labsart.citation.repository;

import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@RequestScoped
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
}
