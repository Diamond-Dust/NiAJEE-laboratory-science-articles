package pl.edu.pg.eti.kask.labsart.scientist.authorisation.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.scientist.context.ScientistContext;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AuthenticationService {

    private ScientistService service;
    private ScientistContext context;

    @Inject
    public AuthenticationService(ScientistService service, ScientistContext context) {
        this.service = service;
        this.context = context;
    }

    public boolean authenticate(String login, String password) {
        Optional<Scientist> user = service.findAuth(login, Util.hash(password));
        if (user.isPresent()) {
            context.setLogged(user.get().getLogin());
            return true;
        } else {
            return false;
        }
    }

    public String getLogged() {
        return context.getLogged();
    }

}
