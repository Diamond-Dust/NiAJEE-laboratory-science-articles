package pl.edu.pg.eti.kask.labsart.scientist.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.entity.UserRoles;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed(UserRoles.USER)
public class ScientistService {

    private ScientistRepository repository;

    private SecurityContext securityContext;

    private Pbkdf2PasswordHash pbkdf;

    //-----------------------------------------------

    @Inject
    public ScientistService(ScientistRepository repository, SecurityContext context, Pbkdf2PasswordHash pbkdf) {
        this.repository = repository;
        this.securityContext = context;
        this.pbkdf = pbkdf;
    }

    //-----------------------------------------------


    public Optional<Scientist> find(String login) {
        return repository.find(login);
    }


    public List<Scientist> findAll() { return repository.findAll(); }

    @PermitAll
    public void create(Scientist user) {
        if (!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            user.setRoles(List.of(UserRoles.USER));
        }
        user.setPassword(pbkdf.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }


    public void update(Scientist entity) {
        repository.update(entity);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(Scientist user) {
        repository.delete(user);
    }

    //-----------------------------------------------

    public Optional<Scientist> findAuth(String login, String password) {
        return repository.findAuth(login, password);
    }

    public boolean isCaller(String role) {
        if (securityContext.getCallerPrincipal() != null) {
            Optional<Scientist> found = find(securityContext.getCallerPrincipal().getName());
            if (found.isPresent()) {
                return found.get().getRoles().contains(role);
            }
        }
        return false;
    }

    public Optional<Scientist> findCallerPrincipal() {
        if (securityContext.getCallerPrincipal() != null) {
            return find(securityContext.getCallerPrincipal().getName());
        } else {
            return Optional.empty();
        }
    }

    public void updateNonNullId(Scientist scientist, String login) {
        find(login).ifPresentOrElse(
                original -> {repository.update(Scientist.nonNullUpdateMapper().apply(original, scientist));},
                () -> { throw new NullPointerException();}
        );
    }

}
