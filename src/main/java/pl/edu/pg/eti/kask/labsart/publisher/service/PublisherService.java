package pl.edu.pg.eti.kask.labsart.publisher.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.publisher.repository.PublisherRepository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class PublisherService {

    private PublisherRepository repository;

    //-----------------------------------------------

    @Inject
    public PublisherService(PublisherRepository repository) {
        this.repository = repository;
    }

    //-----------------------------------------------


    public Optional<Publisher> find(Long id) {
        return repository.find(id);
    }


    public List<Publisher> findAll() { return repository.findAll(); }


    public void create(Publisher publisher) {
        repository.create(publisher);
    }

    public void delete(Publisher entity) {
        repository.delete(entity);
    }

    public void update(Publisher entity) {
        repository.update(entity);
    }

    //-----------------------------------------------

}