package pl.edu.pg.eti.kask.labsart.configuration;

import pl.edu.pg.eti.kask.labsart.datastore.DataStore;
import pl.edu.pg.eti.kask.labsart.scientist.repository.ScientistRepository;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataStore dataSource = (DataStore) sce.getServletContext().getAttribute("dataSource");

        sce.getServletContext().setAttribute("scientistService", new ScientistService(new ScientistRepository(dataSource)));

    }

}
