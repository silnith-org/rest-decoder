package org.silnith.rest.jaxrs.json;

import java.net.URI;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.silnith.rest.jaxrs.json.model.HierarchicalURI;
import org.silnith.rest.jaxrs.json.model.OpaqueURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Component
@Path("uri")
@Api(tags = { "URI" })
public class URIResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(URIResource.class);
    
    @Path("encode/hierarchical")
    @POST
    @Produces({ MediaType.TEXT_PLAIN })
    @ApiOperation(value = "Encode an hierarchical URL.",
            notes = "Encodes an hierarchical URL from the component parts.")
    public String encodeHierarchical(
            @ApiParam(value = "The network protocol", example = "https")
            @QueryParam("scheme") @DefaultValue("http") final String scheme,
            @ApiParam(value = "The user information")
            @QueryParam("userinfo") final String userInfo,
            @ApiParam(value = "The host name or address", example = "silnith.org")
            @QueryParam("host") @NotNull final String host,
            @ApiParam(value = "The port, or -1 for the default", example = "8080")
            @QueryParam("port") final Integer port,
            @ApiParam(value = "Path segments separated by newlines (it is not possible to embed newlines yet)")
            @QueryParam("pathSegment") final List<String> pathSegments,
            @ApiParam(value = "The fragment identifier")
            @QueryParam("fragment") final String fragment) {
        LOG.trace("entering encodeHierarchical({}, {}, {}, {}, {}, {})", scheme,
                userInfo, host, port, pathSegments, fragment);
                
        final UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        
        builder.scheme(scheme);
        builder.userInfo(userInfo);
        builder.host(host);
        if (port != null) {
            builder.port(port);
        }
        for (final String pathSegment : pathSegments) {
            builder.pathSegment(pathSegment);
        }
        builder.fragment(fragment);
        
        final String uriString = builder.toUriString();
        
        LOG.trace("{} returned from encodeHierarchical({}, {}, {}, {}, {}, {})",
                uriString, scheme, userInfo, host, port, pathSegments, fragment);
        return uriString;
    }
    
    @Path("encode/opaque")
    @POST
    @Produces({ MediaType.TEXT_PLAIN })
    @ApiOperation(value = "Encode an opaque URL.", notes = "Encodes an opaque URL from the component parts.")
    public String encodeOpaque(
            @ApiParam(value = "The scheme", example = "mailto")
            @QueryParam("scheme") @NotNull final String scheme,
            @ApiParam(value = "The scheme-specific part", example = "silnith@gmail.com")
            @QueryParam("schemeSpecificPart") @NotNull final String schemeSpecificPart,
            @ApiParam(value = "The fragment identifier")
            @QueryParam("fragment") final String fragment) {
        LOG.trace("entering encodeOpaque({}, {}, {})", scheme, schemeSpecificPart, fragment);
        
        final UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        
        builder.scheme(scheme);
        builder.schemeSpecificPart(schemeSpecificPart);
        builder.fragment(fragment);
        
        final String uriString = builder.toUriString();
        
        LOG.trace("{} returned from encodeOpaque({}, {}, {})", uriString, scheme, schemeSpecificPart, fragment);
        return uriString;
    }
    
    @Path("decode")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Decode an URL.", notes = "Decodes an URL into its component parts.")
    public Object decode(@QueryParam("uri") @NotNull final URI uri) {
        LOG.trace("entering decode({})", uri);
        
        if (uri.isOpaque()) {
            return decodeOpaque(uri);
        } else {
            return decodeHierarchical(uri);
        }
    }
    
    private OpaqueURI decodeOpaque(@NotNull final URI uri) {
        LOG.trace("entering decodeOpaque({})", uri);
        
        final String scheme = uri.getScheme();
        final String schemeSpecificPart = uri.getSchemeSpecificPart();
        final String fragment = uri.getFragment();
        
        final OpaqueURI opaqueURI = new OpaqueURI(scheme, schemeSpecificPart, fragment);
        
        return opaqueURI;
//        return Response.ok(opaqueURI).build();
    }
    
    private HierarchicalURI decodeHierarchical(@NotNull final URI uri) {
        LOG.trace("entering decodeHierarchical({})", uri);
        
        final UriComponents components = UriComponentsBuilder.fromUri(uri).build();
        
        final String scheme = components.getScheme();
        final String userInfo = components.getUserInfo();
        final String host = components.getHost();
        final int port = components.getPort();
        final List<String> pathSegments = components.getPathSegments();
        final MultiValueMap<String, String> queryParams = components.getQueryParams();
        final String fragment = components.getFragment();
        
        final HierarchicalURI hierarchicalURI =
                new HierarchicalURI(scheme, userInfo, host, port, pathSegments, queryParams, fragment);
                
        return hierarchicalURI;
//        return Response.ok(hierarchicalURI).build();
    }
    
}
