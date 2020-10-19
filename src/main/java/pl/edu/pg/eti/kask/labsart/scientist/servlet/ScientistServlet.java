package pl.edu.pg.eti.kask.labsart.scientist.servlet;

import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistGetResponse;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistsGetResponse;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;
import pl.edu.pg.eti.kask.labsart.util.Util;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

public class ScientistServlet extends HttpServlet {
    private final JsonbConfig jsonbConfig = new JsonbConfig().withFormatting(true);
    private final Jsonb       jsonb       = JsonbBuilder.create(jsonbConfig);

    public static class Patterns {
        static final String SCIENTISTS = "^/?$";
        static final String SCIENTIST = "^/[^/]+/?";
    }


    private void getScientist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ScientistService service = (ScientistService) request.getServletContext().getAttribute("scientistService");

        String login = Util.parseRequestPath(request).replaceAll("/", "");
        Optional<Scientist> scientist = service.find(login);

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
        ScientistService service = (ScientistService) request.getServletContext().getAttribute("scientistService");

        response.getWriter()
                .write(jsonb.toJson(ScientistsGetResponse
                                .entityToDtoMapper()
                                .apply(service.findAll())
                        )
                );
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
