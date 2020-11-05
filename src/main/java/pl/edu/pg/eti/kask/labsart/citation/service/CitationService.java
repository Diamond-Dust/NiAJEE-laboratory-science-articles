package pl.edu.pg.eti.kask.labsart.citation.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.repository.CitationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class CitationService {

    private CitationRepository citationRepository;

    //-----------------------------------------------

    @Inject
    public CitationService(CitationRepository citationRepository) {
        this.citationRepository = citationRepository;
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
}
