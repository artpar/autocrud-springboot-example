package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.artpar.curd.SchemaController;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import java.sql.SQLException;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() throws NoSuchMethodException, SQLException {

        HikariConfig haikiConfig = new HikariConfig();
        haikiConfig.setJdbcUrl("jdbc:mysql://localhost:3306/inf?zeroDateTimeBehavior=convertToNull");
        haikiConfig.setUsername("root");
        haikiConfig.setPassword("parth123");
        final Resource.Builder builder = Resource.builder();
        builder.path("test").addMethod("get").handledBy(new Inflector<ContainerRequestContext, Object>() {
            @Override
            public Object apply(ContainerRequestContext containerRequestContext) {
                return "hello";
            }
        });
        registerResources(builder.build());
        registerResources(new SchemaController("/v1", "/v1", new HikariDataSource(haikiConfig), new ObjectMapper()).getRootResource().build());
    }

}