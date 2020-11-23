package pl.edu.pg.eti.kask.labsart.avatar.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.avatar.repository.AvatarRepository;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
@LocalBean
@NoArgsConstructor
public class AvatarService {

    private AvatarRepository    avatarRepository;
    private ScientistRepository scientistRepository;

    //-----------------------------------------------

    @Inject
    public AvatarService(AvatarRepository avatarRepository, ScientistRepository scientistRepository) {
        this.avatarRepository    = avatarRepository;
        this.scientistRepository = scientistRepository;
    }

    //-----------------------------------------------


    public Optional<Avatar> find(UUID id) {
        return avatarRepository.find(id);
    }

    public List<Avatar> findAll() { return avatarRepository.findAll(); }

    public void create(Avatar avatar) {
        avatarRepository.create(avatar);
    }

    public void delete(UUID avatar) {
        avatarRepository.delete(avatarRepository.find(avatar).orElseThrow());
    }

    public void update(Avatar avatar) {
        avatarRepository.update(avatar);
    }

    //-----------------------------------------------

    public boolean createLinkedAvatar(String login, InputStream is) {
        Optional<Scientist> scientist = scientistRepository.find(login);

        if(scientist.isPresent()) {
            if(scientist.get().getAvatar() == null) {
                try {
                    Avatar newAvatar = Avatar.builder().avatar(is.readAllBytes()).build();
                    avatarRepository.create(newAvatar);
                    scientist.get().setAvatar(newAvatar);
                    scientistRepository.update(scientist.get());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    public boolean updateLinkedAvatar(String login, InputStream is) {
        Optional<Scientist> scientist = scientistRepository.find(login);

        if(scientist.isPresent()) {
            UUID id = scientist.get().getAvatar().getId();
            if(id != null) {
                Optional<Avatar> avatar = avatarRepository.find(id);
                if(avatar.isPresent()) {
                    try {
                        avatar.get().setAvatar(is.readAllBytes());
                        avatarRepository.update(avatar.get());
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        }

        return false;
    }

    public boolean deleteLinkedAvatar(String login) {
        Optional<Scientist> scientist = scientistRepository.find(login);

        if(scientist.isPresent()) {
            UUID id = scientist.get().getAvatar().getId();
            if(id != null) {
                Optional<Avatar> avatar = avatarRepository.find(id);
                if(avatar.isPresent()) {
                    avatarRepository.delete(avatar.get());
                    scientist.get().setAvatar(null);
                    scientistRepository.update(scientist.get());
                    return true;
                }
            }
        }

        return false;
    }
}
