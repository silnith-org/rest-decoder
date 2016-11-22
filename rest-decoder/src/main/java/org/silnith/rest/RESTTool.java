package org.silnith.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;


@SpringBootApplication
public class RESTTool {
    
    @Bean
    public DOMImplementationRegistry domImplementationRegistry()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
        final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        return registry;
    }
    
    @Bean
    public DOMImplementation domImplementation(final DOMImplementationRegistry registry) {
        final DOMImplementation domImplementation = registry.getDOMImplementation("Core 2.0");
        return domImplementation;
    }
    
    @Bean
    public DOMImplementationLS domImplementationLS(final DOMImplementationRegistry registry) {
        final DOMImplementation domImplementation = registry.getDOMImplementation("+LS 3.0");
        final Object feature = domImplementation.getFeature("+LS", "3.0");
        final DOMImplementationLS domImplementationLS = (DOMImplementationLS) feature;
        return domImplementationLS;
    }
    
    public static void main(final String[] args) {
        SpringApplication.run(RESTTool.class, args);
    }
    
}
