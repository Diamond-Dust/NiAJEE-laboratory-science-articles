package pl.edu.pg.eti.kask.labsart.scientist.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

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


    public void create(Scientist user) {
        repository.create(user);
    }

    public Optional<Scientist> findAuth(String login, String password) {
        return repository.findAuth(login, password);
    }

    public void updateAvatar(String login, InputStream is) {
        repository.find(login).ifPresent(scientist -> {
            try {
                scientist.setAvatar(is.readAllBytes());
                repository.update(scientist);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }


}
