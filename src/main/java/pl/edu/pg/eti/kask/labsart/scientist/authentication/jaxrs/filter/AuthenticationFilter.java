package pl.edu.pg.eti.kask.labsart.scientist.authentication.jaxrs.filter;

import pl.edu.pg.eti.kask.labsart.scientist.authentication.service.AuthenticationService;
import pl.edu.pg.eti.kask.labsart.scientist.context.ScientistContext;
import pl.edu.pg.eti.kask.labsart.servlet.AuthenticationMethods;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    private AuthenticationService service;

    private ScientistContext context;

    @Inject
    public void setService(AuthenticationService service) {
        this.service = service;
    }

    @Inject
    public void setContext(ScientistContext context) {
        this.context = context;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (context.getPrincipal() == null) {
            //Check for authorization header with basic auth method
            String basic = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (basic == null || !basic.startsWith(AuthenticationMethods.BASIC)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,  String.format(AuthenticationMethods.BASIC_REALM, "Labsart"))
                        .build());
            } else {
                //Parse credentials
                basic = basic.replace(AuthenticationMethods.BASIC, "").trim();
                basic = new String(Base64.getDecoder().decode(basic));
                String[] credentials = basic.split(":");
                //Check credentials
                if (!service.authenticate(credentials[0], credentials[1])) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            }

        }
    }

}

