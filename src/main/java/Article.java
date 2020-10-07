import java.util.HashSet;
import java.util.Set;

public class Article {
    private Publisher         _publisher;
    private Scientist         _author;
    private HashSet<Citation> _sources;
    private String            _title;

    //-----------------------------------------------

    public Article(Article article) {
        _publisher = article._publisher;
        _author    = article._author;
        _sources   = article._sources;
        _title     = article._title;
    }

    public Article(Publisher publisher, Scientist author, HashSet<Citation> sources, String title) {
        _publisher = publisher;
        _author    = author;
        _sources   = sources;
        _title     = title;
    }

    public Article() {
        this(null, null, null, null);
        _sources = new HashSet<Citation>();
    }

    public Article(String title) {
        this(null, null, null, title);
        _sources = new HashSet<Citation>();
    }

    public Article(Publisher publisher, Scientist author, String title) {
        this(publisher, author, null, title);
        _sources = new HashSet<Citation>();
    }

    //-----------------------------------------------

    public void addSource(Citation source) {
        _sources.add(source);
    }

    public void removeSource(Citation source) {
        _sources.remove(source);
    }

    //-----------------------------------------------

    public Publisher getPublisher() {
        return _publisher;
    }

    public Scientist getAuthor() {
        return _author;
    }

    public Set<Citation> getSources() {
        return _sources;
    }

    public String getTitle() {
        return _title;
    }

    public void setPublisher(Publisher publisher) {
        _publisher = publisher;
    }

    public void setAuthor(Scientist author) {
        _author = author;
    }

    public void setSources(HashSet<Citation> sources) {
        _sources = sources;
    }

    public void setTitle(String title) {
        _title = title;
    }

    //-----------------------------------------------

    @Override
    public String toString() {
        return
                "Title: "     + _title                + "\n" +
                "Author: "    + _author.toString()    + "\n" +
                "Publisher: " + _publisher.toString() + "\n" +
                "Sources: "   + _sources.toString()
        ;
    }

}
