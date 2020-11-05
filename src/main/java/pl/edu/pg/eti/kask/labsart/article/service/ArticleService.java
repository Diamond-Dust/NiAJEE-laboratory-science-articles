package pl.edu.pg.eti.kask.labsart.article.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.repository.ArticleRepository;
import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.avatar.repository.AvatarRepository;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.repository.CitationRepository;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class ArticleService {

    private ArticleRepository articleRepository;
    private CitationRepository citationRepository;

    //-----------------------------------------------

    @Inject
    public ArticleService(ArticleRepository articleRepository, CitationRepository citationRepository) {
        this.articleRepository = articleRepository;
        this.citationRepository = citationRepository;
    }

    //-----------------------------------------------


    public Optional<Article> find(Long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() { return articleRepository.findAll(); }

    public void create(Article article) {
        articleRepository.create(article);
    }

    public void delete(Long article) {
        articleRepository.delete(articleRepository.find(article).orElseThrow());
    }

    public void update(Article article) {
        articleRepository.update(article);
    }

    //-----------------------------------------------

    public void deleteCitation(Long articleId, Long citationId) {
        Optional<Article> article = articleRepository.find(articleId);
        Optional<Citation> citation = citationRepository.find(citationId);
        if(article.isPresent() && citation.isPresent()) {
            article.get().getCitations().remove(citation.get());
            articleRepository.update(article.get());
            citationRepository.delete(citation.get());
        }
    }
}

