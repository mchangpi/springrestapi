package com.miltontest.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

  private static String request = "/surveys/1/questions/2";

  @Autowired
  TestRestTemplate template;
  
  @Test
  void getSpecificQuestion_JsonAssert() throws JSONException {
    ResponseEntity<String> response = template.getForEntity(request, String.class);
    //System.out.println(response.getHeaders());
    //System.out.println(response.getBody());
  
    String expected = 
        """
        {   
            "id":"2",
            "description":"Fastest Growing Cloud Platform",
            "options":["AWS","Azure","Google Cloud","Oracle Cloud"]
        }    
        """;

    //assertEquals(expected.trim(), response.getBody());
    JSONAssert.assertEquals(expected, response.getBody(), false);
  }
  
  @Test
  void getSpecificQuestion_HeaderAssert() throws JSONException {
    ResponseEntity<String> response = template.getForEntity(request, String.class);
    //System.out.println(response.getHeaders());
  
    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
  }
  
}
