package com.miltontest.springboot.restapi.survey;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {
  
  private Logger logger = LoggerFactory.getLogger(getClass()); 
  private SurveyService surveyService;
  
  @Autowired
  public SurveyResource(SurveyService service) {
    super();
    this.surveyService = service;
  }
 
  @RequestMapping("surveys")
  public List<Survey> getAllSurveys(){
    return surveyService.getSurveys();
  }

  @RequestMapping("surveys/{id}")
  public Survey getSurvey(@PathVariable String id){
    Survey survey =  surveyService.getSurveyById(id);
    if(null == survey)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return survey;
  }
  
  @RequestMapping("surveys/{id}/questions")
  public List<Question> getAllQuestions(@PathVariable String id){
    List<Question> questions = surveyService.getAllQuestions(id);
    if(null == questions)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return questions;
  }
  
  @RequestMapping("surveys/{sid}/questions/{qid}")
  public Question getSpecificQuestion(@PathVariable String sid, @PathVariable String qid){
    Question question = surveyService.getSpecificQuestion(sid, qid);
    if(null == question)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return question;
  }
  
  @RequestMapping(value = "surveys/{sid}/questions", method = RequestMethod.POST)
  public ResponseEntity<?> addQuestion(@PathVariable String sid, 
      @RequestBody Question question){
    
    logger.debug("id " + sid + " question " + question);
    String qid = surveyService.addQuestion(sid, question);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{qid}").buildAndExpand(qid).toUri();
    return ResponseEntity.created(location).build();
  }
  
  @RequestMapping(value = "surveys/{sid}/questions/{qid}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteQuestion(@PathVariable String sid, @PathVariable String qid){
    surveyService.deleteQuestion(sid, qid);
    return ResponseEntity.noContent().build();
  }
}
