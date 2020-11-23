package pl.edu.pg.eti.kask.labsart.scientist.authentication.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.labsart.scientist.context.ScientistContext;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class AuthenticationService {

    private ScientistService service;

    private ScientistContext context;

    @Inject
    public AuthenticationService(ScientistContext context) {
        this.context = context;
    }

    @EJB
    public void setService(ScientistService service) {
        this.service = service;
    }

    public boolean authenticate(String login, String password) {
        Optional<Scientist> user = service.findAuth(login, Util.hash(password));
        if (user.isPresent()) {
            //Put username in session where it will be present while session is valid.
            context.setPrincipal(user.get().getLogin());
            return true;
        } else {
            return false;
        }
    }

}
