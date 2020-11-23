package pl.edu.pg.eti.kask.labsart.scientist.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.scientist.context.ScientistContext;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
@LocalBean
@NoArgsConstructor
public class ScientistService {

    private ScientistRepository repository;

    private ScientistContext context;

    //-----------------------------------------------

    @Inject
    public ScientistService(ScientistRepository repository, ScientistContext context) {
        this.repository = repository;
        this.context = context;
    }

    //-----------------------------------------------


    public Optional<Scientist> find(String login) {
        return repository.find(login);
    }


    public List<Scientist> findAll() { return repository.findAll(); }

    public void create(Scientist user) {
        repository.create(user);
    }

    public void update(Scientist entity) {
        repository.update(entity);
    }

    public void delete(Scientist user) {
        repository.delete(user);
    }

    //-----------------------------------------------

    public Optional<Scientist> findAuth(String login, String password) {
        return repository.findAuth(login, password);
    }


    public Optional<Scientist> findCallerPrincipal() {
        if (context.getPrincipal() != null) {
            return find(context.getPrincipal());
        } else {
            return Optional.empty();
        }
    }

    public void updateNonNullId(Scientist scientist, String login) {
        find(login).ifPresentOrElse(
                original -> {repository.update(Scientist.nonNullUpdateMapper().apply(original, scientist));},
                () -> { throw new NullPointerException();}
        );
    }

}
