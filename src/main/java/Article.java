import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Article {
    private Publisher         _publisher;
    private Scientist         _author;
    private Set<Citation>     _sources = new HashSet<>();
    private String            _title;

    //-----------------------------------------------

    public Article(Article article) {
        _publisher = article._publisher;
        _author    = article._author;
        _sources   = article._sources;
        _title     = article._title;
    }

    public Article(String title) {
        this(null, null, null, title);
        _sources = new HashSet<>();
    }

    public Article(Publisher publisher, Scientist author, String title) {
        this(publisher, author, null, title);
        _sources = new HashSet<>();
    }

    //-----------------------------------------------

    public void addSource(Citation source) {
        _sources.add(source);
    }

    public void removeSource(Citation source) {
        _sources.remove(source);
    }

    //-----------------------------------------------

}
