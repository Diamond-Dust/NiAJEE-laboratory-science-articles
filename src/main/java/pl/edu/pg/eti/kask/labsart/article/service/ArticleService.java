package pl.edu.pg.eti.kask.labsart.article.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.repository.ArticleRepository;
import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.avatar.repository.AvatarRepository;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.repository.CitationRepository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.UserRoles;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed(UserRoles.USER)
public class ArticleService {

    private ArticleRepository articleRepository;
    private CitationRepository citationRepository;
    private ScientistRepository scientistRepository;

    private SecurityContext securityContext;

    //-----------------------------------------------

    @Inject
    public ArticleService(
            ArticleRepository articleRepository,
            CitationRepository citationRepository,
            ScientistRepository scientistRepository,
            SecurityContext securityContext
    ) {
        this.articleRepository = articleRepository;
        this.citationRepository = citationRepository;
        this.scientistRepository = scientistRepository;
        this.securityContext = securityContext;
    }

    //-----------------------------------------------


    public Optional<Article> find(Long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() { return articleRepository.findAll(); }

    public void create(Article article) {
        article.setAuthor(
                scientistRepository.find(securityContext.getCallerPrincipal().getName())
                        .orElseThrow()
        );
        articleRepository.create(article);
    }

    public void delete(Long article) {
        Optional<Article> articleObject = articleRepository.find(article);
        if(articleObject.isPresent()) {
            List<Citation> citations = articleObject.get().getCitations();
            for (Citation citation : citations){
                citationRepository.delete(citation);
            }
        }
        articleRepository.delete(articleRepository.find(article).orElseThrow());
    }

    public void deleteAll() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles){
            delete(article.getId());
        }
    }

    public void update(Article article) {
        articleRepository.update(article);
    }

    //-----------------------------------------------

    public List<Article> findAllForCaller() {
        return articleRepository.findAllForScientist(
                scientistRepository.find(securityContext.getCallerPrincipal().getName())
                        .orElseThrow()
        );
    }

    public Optional<Article> findForCaller(Long id) {
        return articleRepository.findForScientist(
                id,
                scientistRepository.find(securityContext.getCallerPrincipal().getName())
                        .orElseThrow()
        );
    }

    public void deleteForCaller(Long id) throws NullPointerException {
        Optional<Article> article;

        if(securityContext.isCallerInRole(UserRoles.ADMIN)) {
            article = articleRepository.find(id);
        } else {
            article = articleRepository.findForScientist(
                    id,
                    scientistRepository.find(securityContext.getCallerPrincipal().getName())
                            .orElseThrow()
            );
        }

        article.ifPresentOrElse(
                found -> {delete(found.getId());},
                () -> { throw new NullPointerException();}
        );
    }

    public void updateNonNullIdForCaller(Article newArticle, Long id) throws NullPointerException {
        Optional<Article> article;

        if(securityContext.isCallerInRole(UserRoles.ADMIN)) {
            article = articleRepository.find(id);
        } else {
            article = articleRepository.findForScientist(
                    id,
                    scientistRepository.find(securityContext.getCallerPrincipal().getName())
                            .orElseThrow()
            );
        }

        article.ifPresentOrElse(
                found -> {updateNonNullId(newArticle, found.getId());},
                () -> { throw new NullPointerException();}
        );
    }

    //-----------------------------------------------

    public void createWithoutId(Article article) {
        create(article);
    }

    public void updateNonNullId(Article article, Long id) {
        find(id).ifPresentOrElse(
                original -> {articleRepository.update(Article.nonNullUpdateMapper().apply(original, article));},
                () -> { throw new NullPointerException();}
        );
    }

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

