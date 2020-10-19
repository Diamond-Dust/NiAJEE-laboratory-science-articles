package pl.edu.pg.eti.kask.labsart.scientist.repository;

import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.repository.Repository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import java.util.List;
import java.util.Optional;

public class ScientistRepository implements Repository<Scientist, String> {
    private DataStore store;

    //-----------------------------------------------

    public ScientistRepository(DataStore store) {
        this.store = store;
    }

    //-----------------------------------------------

    @Override
    public Optional<Scientist> find(String login) {
        return store.findScientist(login);
    }

    @Override
    public List<Scientist> findAll() {
        return store.findAllScientists();
    }

    @Override
    public void create(Scientist entity) {
        store.createScientist(entity);
    }

    @Override
    public void delete(Scientist entity) {
        store.deleteScientist(entity);
    }

    @Override
    public void update(Scientist entity) {
        store.updateScientist(entity);
    }
}
