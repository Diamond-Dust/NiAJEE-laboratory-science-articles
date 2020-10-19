package pl.edu.pg.eti.kask.labsart.scientist.service;

import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import java.util.List;
import java.util.Optional;

public class ScientistService {

    private ScientistRepository repository;

    //-----------------------------------------------

    public ScientistService(ScientistRepository repository) {
        this.repository = repository;
    }

    //-----------------------------------------------


    public Optional<Scientist> find(String login) {
        return repository.find(login);
    }


    public List<Scientist> findAll() {
        return repository.findAll();
    }


    public void create(Scientist user) {
        repository.create(user);
    }

}
