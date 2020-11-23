package pl.edu.pg.eti.kask.labsart.publisher.repository;

import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Dependent
public class PublisherRepository implements Repository<Publisher, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    //-----------------------------------------------

    //-----------------------------------------------

    @Override
    public Optional<Publisher> find(Long id) {
        return Optional.ofNullable(em.find(Publisher.class, id));
    }

    @Override
    public List<Publisher> findAll() {
        return em.createQuery("select p from Publisher p", Publisher.class).getResultList();
    }

    @Override
    public void create(Publisher entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Publisher entity) {
        em.remove(em.find(Publisher.class, entity.getId()));
    }

    @Override
    public void update(Publisher entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Publisher entity) {
        em.detach(entity);
    }

    //-----------------------------------------------



}
