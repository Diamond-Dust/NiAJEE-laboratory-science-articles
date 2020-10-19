package pl.edu.pg.eti.kask.labsart.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<E, K> {
    Optional<E> find(K id);
    List<E>     findAll();
    void        create(E entity);
    void        delete(E entity);
    void        update(E entity);
}
