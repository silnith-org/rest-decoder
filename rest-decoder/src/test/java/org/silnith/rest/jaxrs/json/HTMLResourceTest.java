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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HTMLResourceTest {
    
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
    public void testEncodeHTMLAttribute() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testDecodeHTMLAttribute() {
        final ResponseEntity<String> entity = restTemplate.getForEntity("/convert/html/attribute/decode", String.class);
        
        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }
    
}
