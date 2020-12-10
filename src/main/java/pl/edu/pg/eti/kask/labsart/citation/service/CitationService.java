package pl.edu.pg.eti.kask.labsart.citation.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.repository.ArticleRepository;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.logger.LoggedCitation.LoggedCitation;
import pl.edu.pg.eti.kask.labsart.citation.logger.LoggedId.LoggedId;
import pl.edu.pg.eti.kask.labsart.citation.repository.CitationRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
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

    @LoggedId(0)
    public void delete(Long citation) {
        citationRepository.delete(citationRepository.find(citation).orElseThrow());
    }

    public void update(Citation citation) {
        citationRepository.update(citation);
    }

    //-----------------------------------------------

    public List<Citation> findAllForArticle(Long articleId) {
        Optional<Article> article = articleRepository.find(articleId);
        if(article.isPresent()) {
            return article.get().getCitations();
        }
        return Collections.emptyList();
    }

    public void createWithoutId(Citation citation, Long articleId) {
        createCitation(articleId, citation);
    }

    public void updateNonNullId(Citation citation, Long id, Long articleId) {
        find(id).ifPresentOrElse(
                original -> {
                    articleRepository.find(articleId).ifPresentOrElse(
                        article -> {
                            if(article.getCitations().contains(original)) {
                                article.getCitations().remove(original);
                                article.getCitations().add(Citation.nonNullUpdateMapper().apply(original, citation));
                                articleRepository.update(article);
                            }
                        },
                        () -> { throw new NullPointerException();}
                    );
                    citationRepository.update(Citation.nonNullUpdateMapper().apply(original, citation));
                },
                () -> { throw new NullPointerException();}
        );
    }

    public void deleteCitationForArticle(Long citationId, Long articleId) {
        Optional<Article> article = articleRepository.find(articleId);
        Optional<Citation> cit = citationRepository.find(citationId);
        if(cit.isPresent() && article.isPresent()) {
            article.get().getCitations().remove(cit.get());
            articleRepository.update(article.get());

            citationRepository.delete(cit.get());
        }
    }

    public void deleteAllForArticle(Long articleId) {
        Optional<Article> article = articleRepository.find(articleId);
        if (article.isPresent()) {
            List<Citation> citations = article.get().getCitations();
            for (Citation citation : citations) {
                article.get().getCitations().remove(citation);
                citationRepository.delete(citation);
            }
            articleRepository.update(article.get());
        }
    }

    @LoggedCitation(1)
    public void createCitation(Long articleId, Citation citation) {
        Optional<Article> article = articleRepository.find(articleId);
        if(article.isPresent()) {
            citationRepository.create(citation);
            article.get().getCitations().add(citation);
            articleRepository.update(article.get());
        }
    }

    @LoggedCitation(1)
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
            newArticle.getCitations().remove(cit.get());

            citation.setArticle(newArticle);
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
}
