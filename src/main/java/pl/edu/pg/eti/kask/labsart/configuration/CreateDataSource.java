package pl.edu.pg.eti.kask.labsart.configuration;

import pl.edu.pg.eti.kask.labsart.datastore.DataStore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("dataSource", new DataStore());
    }

}
