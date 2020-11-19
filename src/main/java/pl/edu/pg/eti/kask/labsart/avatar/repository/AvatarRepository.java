package pl.edu.pg.eti.kask.labsart.avatar.repository;

import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestScoped
public class AvatarRepository  implements Repository<Avatar, UUID> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    //-----------------------------------------------

    //-----------------------------------------------

    @Override
    public Optional<Avatar> find(UUID id) {
        return Optional.ofNullable(em.find(Avatar.class, id));
    }

    @Override
    public List<Avatar> findAll() {
        return em.createQuery("select a from Avatar a", Avatar.class).getResultList();
    }

    @Override
    public void create(Avatar entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Avatar entity) {
        em.remove(em.find(Avatar.class, entity.getId()));
    }

    @Override
    public void update(Avatar entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Avatar entity) {
        em.detach(entity);
    }
}