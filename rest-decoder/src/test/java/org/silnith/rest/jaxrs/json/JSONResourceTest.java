package org.silnith.rest.jaxrs.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
public class JSONResourceTest {
    
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
    public void testDecode() {
        final HttpEntity<String> postEntity = new HttpEntity<>("\"This is a test.\"");
        
        final ResponseEntity<String> entity = restTemplate.postForEntity("/convert/json/decode", postEntity, String.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("This is a test.", entity.getBody());
    }
    
    @Test
    public void testEncode() {
        final HttpEntity<String> postEntity = new HttpEntity<>("This is a test.");
        
        final ResponseEntity<String> entity = restTemplate.postForEntity("/convert/json/encode", postEntity, String.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("[\"This is a test.\"]", entity.getBody());
    }
    
}
