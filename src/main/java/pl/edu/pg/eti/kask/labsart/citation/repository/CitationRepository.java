package pl.edu.pg.eti.kask.labsart.citation.repository;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class CitationRepository  implements Repository<Citation, Long> {
    private DataStore store;

    //-----------------------------------------------

    @Inject
    public CitationRepository(DataStore store) {
        this.store = store;
    }

    //-----------------------------------------------

    @Override
    public Optional<Citation> find(Long id) {
        return store.findCitation(id);
    }

    @Override
    public List<Citation> findAll() {
        return store.findAllCitations();
    }

    @Override
    public void create(Citation entity) {
        store.createCitation(entity);
    }

    @Override
    public void delete(Citation entity) {
        store.deleteCitation(entity);
    }

    @Override
    public void update(Citation entity) {
        store.updateCitation(entity);
    }
}
