package pl.edu.pg.eti.kask.labsart.scientist.authorisation.servlet;

import pl.edu.pg.eti.kask.labsart.scientist.authorisation.service.AuthenticationService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class AuthenticationServlet extends HttpServlet {

    private AuthenticationService service;

    @Inject
    public AuthenticationServlet(AuthenticationService service) {
        this.service = service;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        response.getWriter().write(servletPath);
        if ("/api/scientist/login".equals(servletPath)) {
            if (!service.authenticate(
                    request.getParameter("login"),
                    request.getParameter("password")
                )
            ) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else
                response.getWriter().write(service.getLogged());
        } else if ("/api/scientist/logout".equals(servletPath)) {
            request.getSession().invalidate();
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}

