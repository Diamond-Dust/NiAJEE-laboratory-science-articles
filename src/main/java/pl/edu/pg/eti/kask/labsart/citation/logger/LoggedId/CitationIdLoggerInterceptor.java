package pl.edu.pg.eti.kask.labsart.citation.logger.LoggedId;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.logging.Logger;

@Interceptor
@LoggedId
@Priority(Interceptor.Priority.APPLICATION + 1)
public class CitationIdLoggerInterceptor implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CitationIdLoggerInterceptor.class.getName());

    private SecurityContext securityContext;

    @Inject
    public void setService(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object processMethod(InvocationContext context) throws Exception {

        try {
            final LoggedId annotation = context.getMethod().getAnnotation(LoggedId.class);
            String msg =
                    "User:" + securityContext.getCallerPrincipal()
                            + " called action: " + context.getMethod().getName()
                            + " with ID: " + context.getParameters()[annotation.value()];
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