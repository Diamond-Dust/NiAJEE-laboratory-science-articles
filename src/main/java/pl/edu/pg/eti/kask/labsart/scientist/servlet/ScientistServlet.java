package pl.edu.pg.eti.kask.labsart.scientist.servlet;

import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistGetResponse;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistsGetResponse;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.JsonbException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

public class ScientistServlet extends HttpServlet {

    private ScientistService scientistService;

    @Inject
    public ScientistServlet(ScientistService scientistService) {
        this.scientistService = scientistService;
    }

    public static class Patterns {
        static final String SCIENTISTS = "^/?$";
        static final String SCIENTIST = "^/[^/]+/?$";
    }

    private final JsonbConfig jsonbConfig = new JsonbConfig().withFormatting(true);
    private final Jsonb       jsonb       = JsonbBuilder.create(jsonbConfig);
    private final Pattern     pattern     = Pattern.compile(Patterns.SCIENTIST);

    private void getScientist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = Util.getFirstGroupFromPath(
                Util.parseRequestPath(request),
                pattern
        );

        Optional<Scientist> scientist = scientistService.find(login);

        if (scientist.isPresent()) {
            try {
                response.getWriter()
                        .write(jsonb.toJson(
                                ScientistGetResponse
                                        .entityToDtoMapper()
                                        .apply(scientist.get())
                                )
                        );
            }
            catch(JsonbException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private void getScientists(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.getWriter()
                    .write(jsonb.toJson(ScientistsGetResponse
                                    .entityToDtoMapper()
                                    .apply(scientistService.findAll())
                            )
                    );
        }
        catch(JsonbException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Util.parseRequestPath(request);
        response.setContentType("application/json");

        if (path.matches(Patterns.SCIENTIST)) {
           getScientist(request, response);
        } else if (path.matches(Patterns.SCIENTISTS)) {
           getScientists(request, response);
        } else {
           response.getWriter().write("{}");
        }
    }

}
