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


@Component
@Path("html")
@Api(tags = { "HTML" })
public class HTMLResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(HTMLResource.class);

    private static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

    private static final Pattern PATTERN = Pattern.compile("<div class=(.*)/>");

    private final DOMImplementation domImplementation;
    
    private final DOMImplementationLS domImplementationLS;

    @Inject
    public HTMLResource(@NotNull final DOMImplementation domImplementation, @NotNull final DOMImplementationLS domImplementationLS) {
        super();
        this.domImplementation = domImplementation;
        this.domImplementationLS = domImplementationLS;
    }
    
    @Path("attribute/encode")
    @POST
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.TEXT_PLAIN })
    public String encodeHTMLAttribute(@NotNull final String text) {
        LOG.trace("entering encodeHTMLAttribute({})", text);
        
        final Document document = domImplementation.createDocument(HTML_NAMESPACE, "html", null);
        final Attr classAttribute = document.createAttribute("class");
        classAttribute.setValue(text);
        
        final Element divElement = document.createElement("div");
        divElement.setAttributeNode(classAttribute);
        
        final Element bodyElement = document.createElement("body");
        bodyElement.appendChild(divElement);
        
        final Element documentElement = document.getDocumentElement();
        documentElement.appendChild(bodyElement);
        
        final LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        final DOMConfiguration domConfig = lsSerializer.getDomConfig();
        domConfig.setParameter("xml-declaration", false);
        
        final String serialized = lsSerializer.writeToString(divElement);
        
        LOG.debug(serialized);
        
        final Matcher matcher = PATTERN.matcher(serialized);
        if (matcher.matches()) {
            final String group = matcher.group(1);
            
            LOG.trace("{} returned from encodeHTMLAttribute({})", group, text);
            return group;
        }
        
        throw new IllegalArgumentException("Unable to encode as HTML attribute: " + text);
    }
    
    @Path("attribute/decode")
    @POST
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.TEXT_PLAIN })
    public String decodeHTMLAttribute(@NotNull final String text) {
        LOG.trace("entering decodeHTMLAttribute({})", text);
        
//        final Document document = domImplementation.createDocument(HTML_NAMESPACE, "html", null);
//        
//        final Element titleElement = document.createElement("title");
//        titleElement.appendChild(document.createTextNode("Parse"));
//        
//        final Element headElement = document.createElement("head");
//        headElement.appendChild(titleElement);
//        
//        final Element bodyElement = document.createElement("body");
//        
//        final Element documentElement = document.getDocumentElement();
//        documentElement.appendChild(headElement);
//        documentElement.appendChild(bodyElement);
        
        final LSInput lsInput = domImplementationLS.createLSInput();
//        lsInput.setStringData("<div class=" + text + "></div>");
        lsInput.setStringData("<html><head><title>Foo</title></head><body><div class=" + text + "></div></body></html>");
        
        final LSParser lsParser = domImplementationLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, "http://www.w3.org/2001/XMLSchema");
//        lsParser.parseWithContext(lsInput, bodyElement, LSParser.ACTION_REPLACE_CHILDREN);
        final Document parse = lsParser.parse(lsInput);
        final Element documentElement = parse.getDocumentElement();
        final Node lastChild = documentElement.getLastChild();
        if (lastChild.getNodeType() == Node.ELEMENT_NODE) {
            final Element bodyElement = (Element) lastChild;
            
            final Node firstChild = bodyElement.getFirstChild();
            if (firstChild.getNodeType() == Node.ELEMENT_NODE) {
                final Element divElement = (Element) firstChild;
                final String attribute = divElement.getAttribute("class");
                
                LOG.trace("{} returned from decodeHTMLAttribute({})", attribute, text);
                return attribute;
            }
        }
        
        throw new IllegalArgumentException("Unable to decode as HTML attribute: " + text);
    }
    
}
