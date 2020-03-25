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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class XMLResourceTest {
    
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
    public void testEncodeXMLAttribute() {
        final HttpEntity<String> requestEntity = new HttpEntity<String>("This is a test.");
        
        final ResponseEntity<String> entity = restTemplate.postForEntity("/convert/xml/attribute/encode", requestEntity, String.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        
        final String body = entity.getBody();
        assertEquals("\"This is a test.\"", body);
    }
    
    @Test
    public void testDecodeXMLAttribute() {
        final HttpEntity<String> requestEntity = new HttpEntity<String>("'This is a test.'");
        
        final ResponseEntity<String> entity = restTemplate.postForEntity("/convert/xml/attribute/decode", requestEntity, String.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        
        final String body = entity.getBody();
        assertEquals("This is a test.", body);
    }
    
}
