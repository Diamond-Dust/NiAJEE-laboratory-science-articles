package pl.edu.pg.eti.kask.labsart.scientist.context;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class ScientistContext implements Serializable {

    @Getter
    @Setter
    private String principal;

    // Anyone can see anything, so we just check whether someone is logged
    public boolean isAuthorized() {
        return principal != null;
    }

}
