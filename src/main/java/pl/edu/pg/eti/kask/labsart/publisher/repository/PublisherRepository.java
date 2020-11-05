package pl.edu.pg.eti.kask.labsart.publisher.repository;

import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class PublisherRepository implements Repository<Publisher, Long> {
    private DataStore store;

    //-----------------------------------------------

    @Inject
    public PublisherRepository(DataStore store) {
            this.store = store;
    }

    //-----------------------------------------------

    @Override
    public Optional<Publisher> find(Long id) {
            return store.findPublisher(id);
    }

    @Override
    public List<Publisher> findAll() {
            return store.findAllPublishers();
    }

    @Override
    public void create(Publisher entity) {
            store.createPublisher(entity);
    }

    @Override
    public void delete(Publisher entity) {
            store.deletePublisher(entity);
    }

    @Override
    public void update(Publisher entity) {
            store.updatePublisher(entity);
    }

    //-----------------------------------------------



}
