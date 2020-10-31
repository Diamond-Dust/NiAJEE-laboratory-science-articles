package pl.edu.pg.eti.kask.labsart.avatar.servlet;


import pl.edu.pg.eti.kask.labsart.avatar.entity.Avatar;
import pl.edu.pg.eti.kask.labsart.avatar.service.AvatarService;
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
import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Pattern;

import static pl.edu.pg.eti.kask.labsart.util.Util.getLoginFromPath;

@MultipartConfig(maxFileSize = 1024 * 1024)
public class AvatarServlet extends HttpServlet {

    private ScientistService scientistService;
    private AvatarService    avatarService;

    @Inject
    public AvatarServlet(ScientistService scientistService, AvatarService avatarService) {
        this.scientistService = scientistService;
        this.avatarService    = avatarService;
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
        if (path.matches(Patterns.AVATAR)) {
            getPortrait(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Util.parseRequestPath(request);
        if (path.matches(Patterns.AVATAR)) {
            putPortrait(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Util.parseRequestPath(request);
        if (path.matches(Patterns.AVATAR)) {
            postPortrait(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Util.parseRequestPath(request);
        if (path.matches(Patterns.AVATAR)) {
            deletePortrait(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private void postPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = getLoginFromPath(request, pattern);
        Optional<Scientist> scientist = scientistService.find(login);

        if (scientist.isPresent()) {
            InputStream is = Util.getInputStreamFromRequest(request, Parameters.AVATAR);
            if(is == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                if(avatarService.createLinkedAvatar(login, is)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = getLoginFromPath(request, pattern);
        Optional<Scientist> scientist = scientistService.find(login);

        if (scientist.isPresent()) {
            InputStream is = Util.getInputStreamFromRequest(request, Parameters.AVATAR);
            if(is == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                if(avatarService.updateLinkedAvatar(login, is)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = getLoginFromPath(request, pattern);
        Optional<Scientist> scientist = scientistService.find(login);

        if (scientist.isPresent()) {
            Optional<Avatar> avatar = avatarService.find(scientist.get().getAvatarId());
            if(avatar.isPresent()) {
                //Type should be stored in the database but in this project we assume everything to be png.
                response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
                response.getOutputStream().write(avatar.get().getAvatar());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deletePortrait(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = getLoginFromPath(request, pattern);
        Optional<Scientist> scientist = scientistService.find(login);

        if (scientist.isPresent()) {
            if(avatarService.deleteLinkedAvatar(login)) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------


}
