package com.miltontest.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

  private static String SPECIFIC_REQUEST = "/surveys/1/questions/2";
  private static String GENERIC_REQUEST = "/surveys/1/questions";

  @Autowired
  TestRestTemplate template;
  
  @Test
  void getSpecificQuestion_JsonAssert() throws JSONException {
  
    HttpEntity<String> httpEntity = new HttpEntity<String>(null, getHttpContentTypeAndAuthHeaders());

    ResponseEntity<String> response = 
        template.exchange(SPECIFIC_REQUEST, HttpMethod.GET, httpEntity, String.class);
    
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
    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
  }
  
  @Test
  void getGenericQuestion_Assert() throws JSONException {
  
    HttpEntity<String> httpEntity = new HttpEntity<String>(null, getHttpContentTypeAndAuthHeaders());

    ResponseEntity<String> response = 
        template.exchange(GENERIC_REQUEST, HttpMethod.GET, httpEntity, String.class);

    String expected = 
        """
            [
              {
              "id": "1"
              },
              {
              "id": "2"
              },
              {
              "id": "3"
              }
            ]
        """;

    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
    JSONAssert.assertEquals(expected, response.getBody(), false);
  }
  
  @Test
  void addAndDeleteQuestion_Assert() {
    String requestBody = """
          {
            "description": "Your Favorite Language",
            "options": [
              "Java",
              "Python",
              "JavaScript",
              "Haskell"
            ],
            "answer": "Java"
          }
        """;

    HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, getHttpContentTypeAndAuthHeaders());

    ResponseEntity<String> responseEntityPost = 
        template.exchange(GENERIC_REQUEST, HttpMethod.POST, httpEntity, String.class);

    assertTrue(responseEntityPost.getStatusCode().is2xxSuccessful());

    String locationHeader = responseEntityPost.getHeaders().get("Location").get(0);
    assertTrue(locationHeader.contains("/surveys/1/questions/"));
    
    //DELETE
    ResponseEntity<String> responseEntityDelete 
    = template.exchange(locationHeader, HttpMethod.DELETE, httpEntity, String.class);

    assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());
  }
  
  private HttpHeaders getHttpContentTypeAndAuthHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", "Basic " + getBasicAuthEncoding("admin", "admin"));
    return headers;
  }
  
  private String getBasicAuthEncoding(String user, String password) {
    String combined = user + ":" + password;
    byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
    return new String(encodedBytes);
  }
  
}
