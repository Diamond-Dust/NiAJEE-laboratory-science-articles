package pl.edu.pg.eti.kask.labsart.datastore;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.serialisation.CloningUtility;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@ApplicationScoped
public class DataStore {
    private Set<Scientist> scientists = new HashSet<>();
    private Set<Article>   articles   = new HashSet<>();
    private Set<Citation>  citations  = new HashSet<>();
    private Set<Publisher> publishers = new HashSet<>();
    private Set<Avatar>    avatars    = new HashSet<>();


    public synchronized List<Scientist> findAllScientists() {
        return scientists.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }
    public synchronized List<Article> findAllArticles() {
        return articles.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }
    public synchronized List<Citation> findAllCitations() {
        return citations.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }
    public synchronized List<Publisher> findAllPublishers() {
        return publishers.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }
    public synchronized List<Avatar> findAllAvatars() {
        return avatars.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }


    public Optional<Scientist> findScientist(String login) {
        return scientists.stream()
                .filter(scientist -> scientist.getLogin().equals(login))
                .findFirst()
                .map(CloningUtility::clone);
    }
    public Optional<Article> findArticle(Long id) {
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }
    public Optional<Citation> findCitation(Long id) {
        return citations.stream()
                .filter(citation -> citation.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }
    public Optional<Publisher> findPublisher(Long id) {
        return publishers.stream()
                .filter(publisher -> publisher.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }
    public Optional<Avatar> findAvatar(UUID id) {
        return avatars.stream()
                .filter(avatar -> avatar.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }


    public synchronized void createScientist(Scientist scientist) throws IllegalArgumentException {
        findScientist(scientist.getLogin()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The scientist login \"%s\" is not unique", scientist.getLogin()));
                },
                () -> {
                    scientists.add(scientist);
                });
    }
    public synchronized void createArticle(Article article) throws IllegalArgumentException {
        findArticle(article.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The article id \"%s\" is not unique", article.getId()));
                },
                () -> articles.add(article));
    }
    public synchronized void createCitation(Citation citation) throws IllegalArgumentException {
        findCitation(citation.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The citation id \"%s\" is not unique", citation.getId()));
                },
                () -> citations.add(citation));
    }
    public synchronized void createPublisher(Publisher publisher) throws IllegalArgumentException {
        findPublisher(publisher.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The publisher id \"%d\" is not unique", publisher.getId()));
                },
                () -> publishers.add(publisher));
    }
    public synchronized void createAvatar(Avatar avatar) throws IllegalArgumentException {
        findAvatar(avatar.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The avatar id \"%s\" is not unique", avatar.getId()));
                },
                () -> {
                    avatars.add(avatar);
                    savePicture(avatar);
                });
    }


    public synchronized void updateScientist(Scientist scientist) throws IllegalArgumentException {
        findScientist(scientist.getLogin()).ifPresentOrElse(
                original -> {
                    scientists.remove(original);
                    scientists.add(scientist);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The scientist with login \"%s\" does not exist", scientist.getLogin()));
                });
    }
    public synchronized void updateArticle(Article article) throws IllegalArgumentException {
        findArticle(article.getId()).ifPresentOrElse(
                original -> {
                    articles.remove(original);
                    articles.add(article);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The article with id \"%d\" does not exist", article.getId()));
                });
    }
    public synchronized void updateCitation(Citation citation) throws IllegalArgumentException {
        findCitation(citation.getId()).ifPresentOrElse(
                original -> {
                    citations.remove(original);
                    citations.add(citation);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The citation with id \"%d\" does not exist", citation.getId()));
                });
    }
    public synchronized void updatePublisher(Publisher publisher) throws IllegalArgumentException {
        findPublisher(publisher.getId()).ifPresentOrElse(
                original -> {
                    publishers.remove(original);
                    publishers.add(publisher);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The publisher with id \"%d\" does not exist", publisher.getId()));
                });
    }
    public synchronized void updateAvatar(Avatar avatar) throws IllegalArgumentException {
        findAvatar(avatar.getId()).ifPresentOrElse(
                original -> {
                    avatars.remove(original);
                    avatars.add(avatar);
                    savePicture(avatar);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The avatar with id \"%s\" does not exist", avatar.getId()));
                });
    }



    public synchronized void deleteScientist(Scientist entity) throws IllegalArgumentException {
        findScientist(entity.getLogin()).ifPresentOrElse(
                original -> scientists.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The scientist with login \"%s\" does not exist", entity.getLogin()));
                });
    }
    public synchronized void deleteArticle(Article entity) throws IllegalArgumentException {
        findArticle(entity.getId()).ifPresentOrElse(
                original -> articles.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The article with id \"%d\" does not exist", entity.getId()));
                });
    }
    public synchronized void deleteCitation(Citation entity) throws IllegalArgumentException {
        findCitation(entity.getId()).ifPresentOrElse(
                original -> citations.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The citation with id \"%d\" does not exist", entity.getId()));
                });
    }
    public synchronized void deletePublisher(Publisher entity) throws IllegalArgumentException {
        findPublisher(entity.getId()).ifPresentOrElse(
                original -> publishers.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The publisher with id \"%d\" does not exist", entity.getId()));
                });
    }
    public synchronized void deleteAvatar(Avatar entity) throws IllegalArgumentException {
        findAvatar(entity.getId()).ifPresentOrElse(
                original -> {
                    avatars.remove(original);
                    deletePicture(original);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The avatar with id \"%s\" does not exist", entity.getId()));
                });
    }



    public Stream<Scientist> getScientistStream() {
        return scientists.stream();
    }
    public Stream<Article> getArticleStream() {
        return articles.stream();
    }
    public Stream<Citation> getCitationStream() {
        return citations.stream();
    }
    public Stream<Publisher> getPublisherStream() {
        return publishers.stream();
    }
    public Stream<Avatar> getAvatarStream() {
        return avatars.stream();
    }

    //------------------------------------------------------------------------------------------------------------------

    public synchronized Optional<Scientist> findScientist(String login, String password) throws IllegalArgumentException {
        return scientists.stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(users -> users.getPassword().equals(password))
                .findFirst()
                .map(CloningUtility::clone);
    }

    private synchronized void savePicture(Avatar avatar) {
        String avatarPath = System.getenv("AV_PATH");
        String avatarFilePath = avatarPath + File.separator + avatar.getId() + ".png";

        try {
            Files.createDirectories(Paths.get(avatarPath));
            File imgPath = new File(avatarFilePath);
            imgPath.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(avatarFilePath)) {
                fos.write(avatar.getAvatar());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void deletePicture(Avatar avatar) {
        String avatarPath = System.getenv("AV_PATH");
        String avatarFilePath = avatarPath + File.separator + avatar.getId() + ".png";

        try {
            Files.createDirectories(Paths.get(avatarPath));
            File imgPath = new File(avatarFilePath);
            imgPath.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
