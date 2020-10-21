package pl.edu.pg.eti.kask.labsart.scientist.servlet;

import pl.edu.pg.eti.kask.labsart.scientist.context.ScientistContext;
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
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScientistServlet extends HttpServlet {

    private ScientistService scientistService;
    private ScientistContext scientistContext;

    @Inject
    public ScientistServlet(ScientistService scientistService, ScientistContext scientistContext) {
        this.scientistService = scientistService;
        this.scientistContext = scientistContext;
    }

    public static class Patterns {
        static final String SCIENTISTS = "^/?$";
        static final String SCIENTIST = "^/[^/]+/?$";
    }

    private final JsonbConfig jsonbConfig = new JsonbConfig().withFormatting(true);
    private final Jsonb       jsonb       = JsonbBuilder.create(jsonbConfig);
    private final Pattern     pattern     = Pattern.compile(Patterns.SCIENTIST);

    private void getScientist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //String login = scientistContext.getLogged();
        String login = Util.getFirstGroupFromPath(
                Util.parseRequestPath(request),
                pattern
        );

        Optional<Scientist> scientist = scientistService.find(login);

        if (scientist.isPresent()) {
            response.getWriter()
                    .write(jsonb.toJson(
                            ScientistGetResponse
                                    .entityToDtoMapper()
                                    .apply(scientist.get())
                            )
                    );
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private void getScientists(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(
                ScientistsGetResponse.entityToDtoMapper()
                    .apply(scientistService.findAll())
                    .getScientistsString()
        );

        try {
            response.getWriter()
                    .write(jsonb.toJson(ScientistsGetResponse
                                    .entityToDtoMapper()
                                    .apply(scientistService.findAll())
                            )
                    );
        }
        catch(JsonbException e) {
            response.getWriter().write("\n\n\n" + e.getMessage() + "\n\n\n" + e.getCause());
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String logged = scientistContext.getLogged();
        String path = Util.parseRequestPath(request);
        response.setContentType("application/json");

        //if(logged != null) {
            if (path.matches(Patterns.SCIENTIST)) {
                getScientist(request, response);
                //return;
            } else if (path.matches(Patterns.SCIENTISTS)) {
                getScientists(request, response);
                //return;
            } else {
                response.getWriter().write("{}");
            }
        //}

        //response.getWriter().write("{}");
    }

}
