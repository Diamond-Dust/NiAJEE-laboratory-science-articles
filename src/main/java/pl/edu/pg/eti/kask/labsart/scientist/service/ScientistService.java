package pl.edu.pg.eti.kask.labsart.scientist.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class ScientistService {

    private ScientistRepository repository;

    //-----------------------------------------------

    @Inject
    public ScientistService(ScientistRepository repository) {
        this.repository = repository;
    }

    //-----------------------------------------------


    public Optional<Scientist> find(String login) {
        return repository.find(login);
    }


    public List<Scientist> findAll() { return repository.findAll(); }


    @Transactional
    public void create(Scientist user) {
        repository.create(user);
    }

    @Transactional
    public void update(Scientist entity) {
        repository.update(entity);
    }

    //-----------------------------------------------

    public Optional<Scientist> findAuth(String login, String password) {
        return repository.findAuth(login, password);
    }


}
