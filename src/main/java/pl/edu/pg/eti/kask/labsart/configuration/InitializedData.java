package pl.edu.pg.eti.kask.labsart.configuration;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;
import pl.edu.pg.eti.kask.labsart.commontypes.Education;
import pl.edu.pg.eti.kask.labsart.commontypes.Specialisation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.publisher.service.PublisherService;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.entity.UserRoles;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.List;

@Singleton
@Startup
public class InitializedData {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    private RequestContextController requestContextController;

    private Pbkdf2PasswordHash pbkdf;

    public InitializedData() {
    }

    @Inject
    public InitializedData (
            RequestContextController requestContextController,
            Pbkdf2PasswordHash pbkdf
    ) {
        this.requestContextController = requestContextController;
        this.pbkdf = pbkdf;
    }

    @PostConstruct
    private synchronized void init() {
        requestContextController.activate();


        Scientist einstein = Scientist.builder()
                .login("einstein33")
                .password(pbkdf.generate("secretbomb".toCharArray()))
                .hirschIndex(30.0)
                .website(Util.createUrl("http://einstein.com"))
                .education(Education.HABILITATION)
                .roles(List.of(UserRoles.USER))
                .build();
        Scientist koch = Scientist.builder()
                .login("koch")
                .password(pbkdf.generate("bacter".toCharArray()))
                .hirschIndex(10.0)
                .website(Util.createUrl("http://kochamkocha.de.pl"))
                .education(Education.DOCTORATE)
                .roles(List.of(UserRoles.USER))
                .build();
        Scientist dijkstra = Scientist.builder()
                .login("dijkstra")
                .password(pbkdf.generate("zoom".toCharArray()))
                .hirschIndex(15.0)
                .website(Util.createUrl("http://consideredharmful.com"))
                .education(Education.MASTER)
                .roles(List.of(UserRoles.USER))
                .build();
        Scientist aaa = Scientist.builder()
                .login("aaa")
                .password(pbkdf.generate("bbb".toCharArray()))
                .hirschIndex(0.0)
                .education(Education.NONE)
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .build();


        em.persist(einstein);
        em.persist(koch);
        em.persist(dijkstra);
        em.persist(aaa);

        Publisher p1 = Publisher.builder()
                .id(123L)
                .impactFactor(2.0)
                .name("Big House")
                .predatory(false)
                .spec(Specialisation.ENGINEERING)
                .build();
        Publisher p2 = Publisher.builder()
                .id(12L)
                .impactFactor(0.2)
                .name("Smol House")
                .predatory(false)
                .spec(Specialisation.ENGINEERING)
                .build();
        Publisher p3 = Publisher.builder()
                .id(13L)
                .impactFactor(5.0)
                .name("CSS")
                .predatory(false)
                .spec(Specialisation.COMPUTER_SCIENCE)
                .build();
        Publisher p4 = Publisher.builder()
                .id(23L)
                .impactFactor(1.0)
                .name("Shark")
                .predatory(true)
                .spec(Specialisation.BIOLOGY)
                .build();

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);


        Article a1 = Article.builder()
                .id(7641L)
                .title("GOTO considered harmful")
                .author(dijkstra)
                .publisher(p3)
                .build();
        Article a2 = Article.builder()
                .id(8743L)
                .title("Smart dolphin")
                .author(aaa)
                .publisher(p4)
                .build();
        Article a3 = Article.builder()
                .id(90L)
                .title("Houses of Glass")
                .author(koch)
                .publisher(p1)
                .build();
        Article a4 = Article.builder()
                .id(421L)
                .title("Relative stuff")
                .author(einstein)
                .publisher(p1)
                .build();


        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);

        p3.getArticles().add(a1);
        p4.getArticles().add(a2);
        p1.getArticles().add(a3);
        p1.getArticles().add(a4);

        em.merge(p1);
        em.merge(p3);
        em.merge(p4);

        dijkstra.getArticles().add(a1);
        aaa.getArticles().add(a2);
        koch.getArticles().add(a3);
        einstein.getArticles().add(a4);

        em.merge(einstein);
        em.merge(koch);
        em.merge(dijkstra);
        em.merge(aaa);


        Citation c1 = Citation.builder()
                .id(93825L)
                .pageNumber(1)
                .source("Smart stuff for CS")
                .article(a1)
                .build();
        Citation c2 = Citation.builder()
                .id(63425L)
                .pageNumber(5)
                .source("Smart stuff for CS")
                .article(a1)
                .build();
        Citation c3 = Citation.builder()
                .id(9325L)
                .pageNumber(74)
                .source("Smart stuff for CS")
                .article(a1)
                .build();
        Citation c4 = Citation.builder()
                .id(13825L)
                .pageNumber(41)
                .source("Big Book")
                .article(a2)
                .build();
        Citation c5 = Citation.builder()
                .id(6315L)
                .pageNumber(2)
                .source("Smol book")
                .article(a2)
                .build();
        Citation c6 = Citation.builder()
                .id(9125L)
                .pageNumber(14)
                .source("Yes He Canned")
                .article(a2)
                .build();

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(c5);
        em.persist(c6);


        a1.getCitations().add(c1);
        a1.getCitations().add(c2);
        a1.getCitations().add(c3);
        a2.getCitations().add(c4);
        a2.getCitations().add(c5);
        a2.getCitations().add(c6);

        em.merge(a1);
        em.merge(a2);

        requestContextController.deactivate();
    }


}
