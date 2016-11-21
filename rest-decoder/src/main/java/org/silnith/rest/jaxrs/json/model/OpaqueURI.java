package org.silnith.rest.jaxrs.json.model;

import java.beans.ConstructorProperties;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.constraints.NotNull;


public class OpaqueURI {
    
    private final String scheme;
    
    private final String schemeSpecificPart;
    
    private final String fragment;
    
    @ConstructorProperties({ "scheme", "schemeSpecificPart", "fragment" })
    public OpaqueURI(@NotNull final String scheme, @NotNull final String schemeSpecificPart, final String fragment) {
        super();
        this.scheme = scheme;
        this.schemeSpecificPart = schemeSpecificPart;
        this.fragment = fragment;
    }
    
    @NotNull
    public String getScheme() {
        return scheme;
    }
    
    @NotNull
    public String getSchemeSpecificPart() {
        return schemeSpecificPart;
    }
    
    public String getFragment() {
        return fragment;
    }
    
    public URI toURI() throws URISyntaxException {
        return new URI(scheme, schemeSpecificPart, fragment);
    }
    
}
