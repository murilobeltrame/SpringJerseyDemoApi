package com.murilo.demojerseyapi;

import com.murilo.demojerseyapi.resources.UserResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;

@Component
@ApplicationPath("/api")
@Path("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        register(UserResource.class);

        packages("com.example.jaxrs.resources");
        register(OpenApiResource.class);
    }
}
