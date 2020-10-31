package pl.edu.pg.eti.kask.labsart.avatar.repository;

import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.repository.Repository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class AvatarRepository  implements Repository<Avatar, UUID> {
    private DataStore store;

    //-----------------------------------------------

    @Inject
    public AvatarRepository(DataStore store) {
        this.store = store;
    }

    //-----------------------------------------------

    @Override
    public Optional<Avatar> find(UUID id) {
        return store.findAvatar(id);
    }

    @Override
    public List<Avatar> findAll() {
        return store.findAllAvatars();
    }

    @Override
    public void create(Avatar entity) {
        store.createAvatar(entity);
    }

    @Override
    public void delete(Avatar entity) {
        store.deleteAvatar(entity);
    }

    @Override
    public void update(Avatar entity) {
        store.updateAvatar(entity);
    }
}