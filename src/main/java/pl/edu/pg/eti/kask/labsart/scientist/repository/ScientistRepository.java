package pl.edu.pg.eti.kask.labsart.scientist.repository;

import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.repository.Repository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ScientistRepository implements Repository<Scientist, String> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    //-----------------------------------------------

    //-----------------------------------------------

    @Override
    public Optional<Scientist> find(String login) {
        return Optional.ofNullable(em.find(Scientist.class, login));
    }

    @Override
    public List<Scientist> findAll() {
        return em.createQuery("select s from Scientist s", Scientist.class).getResultList();
    }

    @Override
    public void create(Scientist entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Scientist entity) {
        em.remove(em.find(Scientist.class, entity.getLogin()));
    }

    @Override
    public void update(Scientist entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Scientist entity) {
        em.detach(entity);
    }

    //-----------------------------------------------

    public Optional<Scientist> findAuth(String login, String password) {
        try {
            return Optional.of(
                    em.createQuery("select s from Scientist s where s.login = :login and s.password = :password", Scientist.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
