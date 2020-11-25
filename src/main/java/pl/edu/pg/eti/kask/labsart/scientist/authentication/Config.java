package pl.edu.pg.eti.kask.labsart.scientist.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Labsart")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Labsart",
        callerQuery = "select password from scientists where login = ?",
        groupsQuery = "select role from users_roles where scientist = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class Config {
}
