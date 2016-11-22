package org.silnith.rest.jaxrs.json;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class URIResourceTest {
    
    @Inject
    private TestRestTemplate restTemplate;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testEncodeHierarchical() {
        final HttpEntity<String> requestEntity = new HttpEntity<>("This is a test.");
        
        final UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/convert/uri/encode/hierarchical");
        builder.queryParam("scheme", "https");
        builder.queryParam("host", "silnith.org");
        builder.queryParam("pathSegment", "foo", "bar", "baz");
        
        final ResponseEntity<String> entity = restTemplate.postForEntity(builder.toUriString(), requestEntity, String.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("https://silnith.org/foo/bar/baz", entity.getBody());
    }
    
    @Test
    public void testEncodeOpaque() {
        final HttpEntity<String> requestEntity = new HttpEntity<>("");
        
        final UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/convert/uri/encode/opaque");
        builder.queryParam("scheme", "mailto");
        builder.queryParam("schemeSpecificPart", "nobody@silnith.org");
        
        final ResponseEntity<String> entity = restTemplate.postForEntity(builder.toUriString(), requestEntity, String.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("mailto:nobody@silnith.org", entity.getBody());
    }
    
    @Test
    public void testDecode() {
        final HttpEntity<String> requestEntity = new HttpEntity<>("");
        
        final UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/convert/uri/decode");
        builder.queryParam("uri", "https://silnith.org/foo/bar");
        
        final ResponseEntity<String> entity = restTemplate.postForEntity(builder.toUriString(), requestEntity, String.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("{}", entity.getBody());
    }
    
}
