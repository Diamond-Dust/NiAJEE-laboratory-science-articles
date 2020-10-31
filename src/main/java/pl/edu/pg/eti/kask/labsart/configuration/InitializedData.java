package pl.edu.pg.eti.kask.labsart.configuration;

import pl.edu.pg.eti.kask.labsart.commontypes.Education;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class InitializedData {

    private final ScientistService scientistService;

    @Inject
    public InitializedData (ScientistService scientistService) {
        this.scientistService = scientistService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {

        Scientist einstein = Scientist.builder()
                .login("einstein33")
                .password(Util.hash("secretbomb"))
                .hirschIndex(30.0)
                .website(Util.createUrl("http://einstein.com"))
                .education(Education.HABILITATION)
                .build();
        Scientist koch = Scientist.builder()
                .login("koch")
                .password(Util.hash("bacter"))
                .hirschIndex(10.0)
                .website(Util.createUrl("http://kochamkocha.de.pl"))
                .education(Education.DOCTORATE)
                .build();
        Scientist dijkstra = Scientist.builder()
                .login("dijkstra")
                .password(Util.hash("zoom"))
                .hirschIndex(15.0)
                .website(Util.createUrl("http://consideredharmful.com"))
                .education(Education.MASTER)
                .build();
        Scientist aaa = Scientist.builder()
                .login("aaa")
                .password(Util.hash("bbb"))
                .hirschIndex(0.0)
                .education(Education.NONE)
                .build();

        scientistService.create(einstein);
        scientistService.create(koch);
        scientistService.create(dijkstra);
        scientistService.create(aaa);
    }


}
