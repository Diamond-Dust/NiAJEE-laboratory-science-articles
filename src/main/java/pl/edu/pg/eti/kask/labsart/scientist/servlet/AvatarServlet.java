package pl.edu.pg.eti.kask.labsart.scientist.servlet;


import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

@MultipartConfig(maxFileSize = 1024 * 1024)
public class AvatarServlet extends HttpServlet {

    private ScientistService service;

    @Inject
    public AvatarServlet(ScientistService service) {
        this.service = service;
    }
    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }
    public static class Patterns {
        public static final String AVATAR = "^/[^/]+/?$";
    }

    public static class Parameters {
        public static final String AVATAR = "avatar";

    }

    private final Pattern pattern     = Pattern.compile(Patterns.AVATAR);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Util.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getPortrait(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Util.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                putPortrait(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void putPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String login = Util.getFirstGroupFromPath(
                Util.parseRequestPath(request),
                pattern
        );
        Optional<Scientist> scientist = service.find(login);

        if (scientist.isPresent()) {
            Part portrait = request.getPart(Parameters.AVATAR);

            if (portrait != null) {
                service.updateAvatar(login, portrait.getInputStream());
            } else if (request.getContentLength() > 0) {
                service.updateAvatar(login, request.getInputStream());
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String login = Util.getFirstGroupFromPath(
                Util.parseRequestPath(request),
                pattern
        );
        Optional<Scientist> scientist = service.find(login);

        if (scientist.isPresent()) {
            //Type should be stored in the database but in this project we assume everything to be png.
            response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            byte[] avatar = scientist.get().getAvatar();
            //response.setContentLength((avatar == null) ? 0 : avatar.length);
            if(avatar != null)
                response.getOutputStream().write(scientist.get().getAvatar());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
