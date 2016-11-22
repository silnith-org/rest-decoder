package org.silnith.rest.jaxrs.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Component
@Path("xml")
@Api(tags = { "XML" })
public class XMLResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(XMLResource.class);
    
    private static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
    
    private static final Pattern PATTERN = Pattern.compile("<div class=(.*)/>");
    
    private final DOMImplementation domImplementation;
    
    private final DOMImplementationLS domImplementationLS;
    
    @Inject
    public XMLResource(@NotNull final DOMImplementation domImplementation,
            @NotNull final DOMImplementationLS domImplementationLS) {
        super();
        this.domImplementation = domImplementation;
        this.domImplementationLS = domImplementationLS;
    }
    
    @Path("attribute/encode")
    @POST
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.TEXT_PLAIN })
    @ApiOperation(value = "Encode an XML attribute.",
            notes = "Encodes any raw text so it can be safely placed into an XML attribute.")
    public String encodeXMLAttribute(
            @ApiParam(value = "The raw text to place inside attribute.") @NotNull final String text) {
        LOG.trace("entering encodeXMLAttribute({})", text);
        
        final Document document = domImplementation.createDocument(HTML_NAMESPACE, "html", null);
        final Attr classAttribute = document.createAttribute("class");
        classAttribute.setValue(text);
        
        final Element divElement = document.createElement("div");
        divElement.setAttributeNode(classAttribute);
        
        final LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        final DOMConfiguration domConfig = lsSerializer.getDomConfig();
        domConfig.setParameter("xml-declaration", false);
        
        final String serialized = lsSerializer.writeToString(divElement);
        
        LOG.debug("serialized form: {}", serialized);
        
        final Matcher matcher = PATTERN.matcher(serialized);
        if (matcher.matches()) {
            final String group = matcher.group(1);
            
            LOG.trace("{} returned from encodeXMLAttribute({})", group, text);
            return group;
        }
        
        throw new IllegalArgumentException("Unable to encode as XML attribute: " + text);
    }
    
    @Path("attribute/decode")
    @POST
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.TEXT_PLAIN })
    @ApiOperation(value = "Decode an XML attribute.",
            notes = "Decodes an XML attribute and returns the raw string it contains.")
    public String decodeXMLAttribute(
            @ApiParam(value = "The XML attribute, including the quotation marks.") @NotNull final String text) {
        LOG.trace("entering decodeXMLAttribute({})", text);
        
        final LSInput lsInput = domImplementationLS.createLSInput();
        lsInput.setStringData(
                "<html><head><title>Foo</title></head><body><div class=" + text + "></div></body></html>");
                
        final LSParser lsParser = domImplementationLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS,
                XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Document parse = lsParser.parse(lsInput);
        final Element documentElement = parse.getDocumentElement();
        final Node lastChild = documentElement.getLastChild();
        if (lastChild.getNodeType() == Node.ELEMENT_NODE) {
            final Element bodyElement = (Element) lastChild;
            
            final Node firstChild = bodyElement.getFirstChild();
            if (firstChild.getNodeType() == Node.ELEMENT_NODE) {
                final Element divElement = (Element) firstChild;
                final String attribute = divElement.getAttribute("class");
                
                LOG.trace("{} returned from decodeXMLAttribute({})", attribute, text);
                return attribute;
            }
        }
        
        throw new IllegalArgumentException("Unable to decode as XML attribute: " + text);
    }
    
}
