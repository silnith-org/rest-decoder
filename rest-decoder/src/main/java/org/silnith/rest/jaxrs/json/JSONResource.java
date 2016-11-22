package org.silnith.rest.jaxrs.json;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Component
@Path("json")
@Api(tags = { "JSON" })
public class JSONResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(JSONResource.class);
    
    @Path("decode")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_PLAIN })
    @ApiOperation(value = "Decodes JSON data.",
            notes = "Parses JSON data and returns the raw value.  This is typically used to undo string escaping.")
    public String decode(
            @ApiParam(value = "A JSON-encoded string, including quotation marks.") @NotNull final Object object) {
        LOG.trace("entering decode({})", object);
        
        final String string = String.valueOf(object);
        
        LOG.trace("{} returned from decode({})", string, object);
        return string;
    }
    
    @Path("encode")
    @POST
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Encodes text as a JSON string.",
            notes = "Takes raw text and returns the escaped form for a JSON string.")
    public Object encode(@ApiParam(value = "Any text.") @NotNull final String text) throws IOException {
        LOG.trace("entering encode({})", text);
        
        final Object[] wrapped = new Object[] { text };
        
        LOG.trace("{} returned from encode({})", wrapped, text);
        return wrapped;
    }
    
}
