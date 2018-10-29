package rest.v1;

import javax.ws.rs.ApplicationPath;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import rest.v1.sources.*;

@OpenAPIDefinition(info = @Info(
        title = "REST API for catalog management",
        version = "v1",
        description = "Manage metadata of artists, tracks, ... in the catalog",
        contact = @Contact(name = "Joze", email = "joze@not-a-real-mail.com"),
        license = @License(name = "")))
@ApplicationPath("v1")
public class SourceFrame extends javax.ws.rs.core.Application {
}
