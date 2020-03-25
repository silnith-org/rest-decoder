package org.silnith.rest.jaxrs;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import io.swagger.jaxrs.config.BeanConfig;


@Component
@ApplicationPath(Settings.BASE_PATH)
public class Settings extends ResourceConfig {
    
    private static final String BASE_PACKAGE = "org.silnith.rest.jaxrs";
    
    static final String BASE_PATH = "convert";
    
    public Settings() {
        packages(BASE_PACKAGE, "io.swagger.jaxrs.listing");
        
        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("REST Conversion Tool");
        beanConfig.setDescription("This service provides endpoints to convert between commonly-used web data types.");
        beanConfig.setPrettyPrint(true);
        beanConfig.setBasePath(BASE_PATH);
        beanConfig.setResourcePackage(BASE_PACKAGE);
        beanConfig.setScan();
    }
    
}
