package com.miltontest.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceMockTest {
  //Mock -> surveyService.getSpecificQuestion(sid, qid);
  
  @MockBean
  private SurveyService surveyService;
  
  @Autowired
  private MockMvc mockMvc;
  
  private static String SPECIFIC_REQUEST = "http://localhost:8080/surveys/1/questions/1";
  private static String GENERIC_REQUEST = "http://localhost:8080/surveys/1/questions";
  
  @Test
  void getSpecificQuestion_Status404() throws Exception {
    RequestBuilder requestBuilder = 
        MockMvcRequestBuilders.get(SPECIFIC_REQUEST).accept(MediaType.APPLICATION_JSON);
    
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println(mvcResult.getResponse().getStatus());
    
    assertEquals(404, mvcResult.getResponse().getStatus());
  }
  
  @Test
  void getSpecificQuestion_Status200() throws Exception {
    RequestBuilder requestBuilder = 
        MockMvcRequestBuilders.get(SPECIFIC_REQUEST).accept(MediaType.APPLICATION_JSON);
    
    Question question = new Question("1", "Most Popular Cloud Platform Today",
        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
    when(surveyService.getSpecificQuestion("1", "1")).thenReturn(question);
    
    //Mock SurveyService and Test against SurveyResource!
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println(mvcResult.getResponse().getStatus());
    
    assertEquals(200, mvcResult.getResponse().getStatus());
    
    String expected = """
        {"id":"1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"answer":"AWS"}
        """;
    JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
  }
  
  @Test
  void addQuestion_Status201() throws Exception{
    String requestBody = """
        {
          "description": "Your Favorite Language",
          "options": [
            "Java",
            "Python",
            "JavaScript",
            "Haskell"
          ],
          "answer": "Python"
        }
      """;
    
    when(surveyService.addQuestion(anyString(), any())).thenReturn("SOME_ID");

    RequestBuilder requestBuilder = 
        MockMvcRequestBuilders.post(GENERIC_REQUEST)
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody).contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();    
    
    String locationHeader = mvcResult.getResponse().getHeader("Location");
    
    assertEquals(201, mvcResult.getResponse().getStatus());
    assertTrue(locationHeader.contains("/surveys/1/questions/SOME_ID"));
  }
}
