package pl.edu.pg.eti.kask.labsart.publisher.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.publisher.repository.PublisherRepository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
@LocalBean
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

    public void delete(Long article) {
        Optional<Publisher> articleObject = repository.find(article);
        repository.delete(repository.find(article).orElseThrow());
    }

    public void update(Publisher entity) {
        repository.update(entity);
    }

    //-----------------------------------------------

    public void createWithoutId(Publisher publisher) {
        repository.create(publisher);
    }

    public void updateNonNullId(Publisher publisher, Long id) {
        find(id).ifPresentOrElse(
                original -> {repository.update(Publisher.nonNullUpdateMapper().apply(original, publisher));},
                () -> { throw new NullPointerException();}
        );
    }

}
