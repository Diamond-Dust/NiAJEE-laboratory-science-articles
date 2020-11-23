package pl.edu.pg.eti.kask.labsart.scientist.authentication.filter;

import pl.edu.pg.eti.kask.labsart.scientist.authentication.service.AuthenticationService;
import pl.edu.pg.eti.kask.labsart.scientist.context.ScientistContext;
import pl.edu.pg.eti.kask.labsart.servlet.AuthenticationMethods;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Base64;

public class AuthenticationFilter extends HttpFilter {

    private AuthenticationService service;

    private ScientistContext context;

    @Inject
    public AuthenticationFilter(AuthenticationService service, ScientistContext context) {
        this.service = service;
        this.context = context;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (context.getPrincipal() == null) {
            //Check for authorization header with basic auth method
            String basic = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (basic == null || !basic.startsWith(AuthenticationMethods.BASIC)) {
                response.setHeader(HttpHeaders.WWW_AUTHENTICATE, String.format(AuthenticationMethods.BASIC_REALM, "Labsart"));
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            //Parse credentials
            basic = basic.replace(AuthenticationMethods.BASIC, "").trim();
            basic = new String(Base64.getDecoder().decode(basic));
            String[] credentials = basic.split(":");
            //Check credentials
            if (!service.authenticate(credentials[0], credentials[1])) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }
        chain.doFilter(request, response);
    }
}

