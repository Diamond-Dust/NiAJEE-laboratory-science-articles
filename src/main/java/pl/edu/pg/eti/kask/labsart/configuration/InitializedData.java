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
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class InitializedData {

    private final ScientistService scientistService;
    private final ArticleService   articleService;
    private final PublisherService publisherService;
    private final CitationService  citationService;

    @Inject
    public InitializedData (ScientistService scientistService, ArticleService articleService, PublisherService publisherService, CitationService citationService) {
        this.scientistService = scientistService;
        this.articleService   = articleService;
        this.publisherService = publisherService;
        this.citationService = citationService;
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

        publisherService.create(p1);
        publisherService.create(p2);
        publisherService.create(p3);
        publisherService.create(p4);

        Citation c1 = Citation.builder()
                .id(93825L)
                .pageNumber(1)
                .source("Smart stuff for CS")
                .build();
        Citation c2 = Citation.builder()
                .id(63425L)
                .pageNumber(5)
                .source("Smart stuff for CS")
                .build();
        Citation c3 = Citation.builder()
                .id(9325L)
                .pageNumber(74)
                .source("Smart stuff for CS")
                .build();
        Citation c4 = Citation.builder()
                .id(13825L)
                .pageNumber(41)
                .source("Big Book")
                .build();
        Citation c5 = Citation.builder()
                .id(6315L)
                .pageNumber(2)
                .source("Smol book")
                .build();
        Citation c6 = Citation.builder()
                .id(9125L)
                .pageNumber(14)
                .source("Yes He Canned")
                .build();

        citationService.create(c1);
        citationService.create(c2);
        citationService.create(c3);
        citationService.create(c4);
        citationService.create(c5);
        citationService.create(c6);

        Article a1 = Article.builder()
                .id(7641L)
                .title("GOTO considered harmful")
                .author(dijkstra)
                .publisher(p3)
                .build();
        a1.getCitations().add(c1);
        a1.getCitations().add(c2);
        a1.getCitations().add(c3);
        Article a2 = Article.builder()
                .id(8743L)
                .title("Smart dolphin")
                .author(aaa)
                .publisher(p4)
                .build();
        a2.getCitations().add(c4);
        a2.getCitations().add(c5);
        a2.getCitations().add(c6);
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

        articleService.create(a1);
        articleService.create(a2);
        articleService.create(a3);
        articleService.create(a4);

    }


}
