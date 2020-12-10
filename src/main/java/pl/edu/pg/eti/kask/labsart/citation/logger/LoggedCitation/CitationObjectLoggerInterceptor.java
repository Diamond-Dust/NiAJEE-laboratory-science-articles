package pl.edu.pg.eti.kask.labsart.citation.logger.LoggedCitation;

import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.logger.LoggedId.LoggedId;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.logging.Logger;

@Interceptor
@LoggedCitation
@Priority(Interceptor.Priority.APPLICATION)
public class CitationObjectLoggerInterceptor implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CitationObjectLoggerInterceptor.class.getName());

    private SecurityContext securityContext;

    @Inject
    public void setService(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object processMethod(InvocationContext context) throws Exception {

        try {
            final LoggedCitation annotation = context.getMethod().getAnnotation(LoggedCitation.class);
            String msg =
                    "User:" + securityContext.getCallerPrincipal().getName()
                            + " called action: " + context.getMethod().getName()
                            + " with ID: " + ((Citation)(context.getParameters()[annotation.value()])).getId();
            LOGGER.info(msg);
            return context.proceed();
        } catch (Exception e) {
            treatException(e, context);
            throw e;

        }

    }

    private void treatException(Exception e, InvocationContext context) {
        LOGGER.severe(e.toString());
    }

}