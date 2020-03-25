package org.silnith.rest.jaxrs.json.model;

import java.beans.ConstructorProperties;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.util.MultiValueMap;


public class HierarchicalURI {
    
    private final String scheme;
    
    private final String userInfo;
    
    private final String host;
    
    private final int port;
    
    private final List<String> pathSegments;
    
    private final MultiValueMap<String, String> queryParameters;
    
    private final String fragment;
    
    @ConstructorProperties({ "scheme", "userInfo", "host", "port", "pathSegments", "queryParameters", "fragment" })
    public HierarchicalURI(@NotNull final String scheme, final String userInfo, @NotNull final String host,
            @Min( -1) @Max(65535) final int port, @NotNull final List<String> pathSegments,
            @NotNull final MultiValueMap<String, String> queryParameters, final String fragment) {
        super();
        this.scheme = scheme;
        this.userInfo = userInfo;
        this.host = host;
        this.port = port;
        this.pathSegments = pathSegments;
        this.queryParameters = queryParameters;
        this.fragment = fragment;
    }
    
    @NotNull
    public String getScheme() {
        return scheme;
    }
    
    public String getUserInfo() {
        return userInfo;
    }
    
    @NotNull
    public String getHost() {
        return host;
    }
    
    @Min( -1)
    @Max(65535)
    public int getPort() {
        return port;
    }
    
    @NotNull
    public List<String> getPathSegments() {
        return pathSegments;
    }
    
    @NotNull
    public MultiValueMap<String, String> getQueryParameters() {
        return queryParameters;
    }
    
    public String getFragment() {
        return fragment;
    }
    
}
