package pl.edu.pg.eti.kask.labsart.citation.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.repository.ArticleRepository;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.model.CitationEditModel;
import pl.edu.pg.eti.kask.labsart.citation.repository.CitationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class CitationService {

    private CitationRepository citationRepository;
    private ArticleRepository  articleRepository;

    //-----------------------------------------------

    @Inject
    public CitationService(CitationRepository citationRepository, ArticleRepository articleRepository) {
        this.citationRepository = citationRepository;
        this.articleRepository = articleRepository;
    }

    //-----------------------------------------------


    public Optional<Citation> find(Long id) {
        return citationRepository.find(id);
    }

    public List<Citation> findAll() { return citationRepository.findAll(); }

    public void create(Citation citation) {
        citationRepository.create(citation);
    }

    public void delete(Long citation) {
        citationRepository.delete(citationRepository.find(citation).orElseThrow());
    }

    public void update(Citation citation) {
        citationRepository.update(citation);
    }

    //-----------------------------------------------

    public void createCitation(Long articleId, Citation citation) {
        Optional<Article> article = articleRepository.find(articleId);
        if(article.isPresent()) {
            article.get().getCitations().add(citation);
            articleRepository.update(article.get());
            citationRepository.create(citation);
        }
    }

    public void updateArticleCitation(Article newArticle, Citation citation) {
        List<Article> articles = articleRepository.findAll();
        Optional<Citation> cit = citationRepository.find(citation.getId());
        if(cit.isPresent()) {
            for (Article article : articles) {
                if(article.getCitations().contains(cit.get())) {
                    article.getCitations().remove(cit.get());
                    articleRepository.update(article);
                }
            }
            citationRepository.update(citation);
            newArticle.getCitations().add(citation);
            articleRepository.update(newArticle);
        }
    }

    public void updateCitation(Citation citation) {
        List<Article> articles = articleRepository.findAll();
        Optional<Citation> cit = citationRepository.find(citation.getId());
        if(cit.isPresent()) {
            for (Article article : articles) {
                if(article.getCitations().contains(cit.get())) {
                    article.getCitations().remove(cit.get());
                    article.getCitations().add(citation);
                    articleRepository.update(article);
                }
            }
            citationRepository.update(citation);
        }
    }

    public Long getNewId() {
        List<Long> citationIds = citationRepository.findAll().stream().map(Citation::getId).collect(Collectors.toList());
        Random r = new Random();
        long generatedLong = r.nextLong();
        //Naiive
        while(citationIds.contains(generatedLong)) {
            generatedLong = r.nextLong();
        }
        return generatedLong;
    }
}
