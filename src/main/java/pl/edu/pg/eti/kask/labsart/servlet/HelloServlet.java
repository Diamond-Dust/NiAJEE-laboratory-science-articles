package pl.edu.pg.eti.kask.labsart.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    private String theText;

    @Override
    public void init(ServletConfig config) throws ServletException {
        theText = config.getInitParameter("text");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String languageHeader = request.getHeader("Accept-Language");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.print("<h1>");
        out.print(String.format(theText, name));
        out.print("</h1>");
    }
}
