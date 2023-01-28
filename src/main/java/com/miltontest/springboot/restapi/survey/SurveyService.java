package com.miltontest.springboot.restapi.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

  private Logger logger = LoggerFactory.getLogger(getClass());
  private static List<Survey> surveys = new ArrayList<>();
  
  static {
    Question question1 = new Question("1", "Most Popular Cloud Platform Today",
        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
    Question question2 = new Question("2", "Fastest Growing Cloud Platform",
        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
    Question question3 = new Question("3", "Most Popular DevOps Tool",
        Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

    List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));
    Survey survey = new Survey("1", "My Favorite Survey", "Description of the Survey", questions);
    surveys.add(survey);
  }

  public static List<Survey> getSurveys() {
    return surveys;
  }

  public Survey getSurveyById(String id) {
    logger.debug("id " + id);
    Predicate <? super Survey> checkSurveyId = survey -> survey.getId().equalsIgnoreCase(id);
    Optional<Survey> optionSurvey = 
        surveys.stream().filter(checkSurveyId).findFirst();
    return optionSurvey.orElseGet(() -> {
        logger.debug("id " + id + " not exist.");
        return new Survey("", "", "", null);
      }); 
  }

  public List<Question> getAllQuestions(String surveyId) {
    Survey survey = getSurveyById(surveyId);
    return Optional.ofNullable(survey.getQuestions()).orElseGet(
        () -> new ArrayList<Question>());
  }

  public Question getSpecificQuestion(String sid, String qid) {
    List<Question> questions = getAllQuestions(sid);
    if(null == questions) return new Question();

    Optional<Question> optionalQuestion = 
        questions.stream().filter(q -> q.getId().equalsIgnoreCase(qid)).findFirst();
    return optionalQuestion.orElseGet(() -> new Question());
  }

  public String addQuestion(String sid, Question question) {
    List<Question> questions = getAllQuestions(sid);
    question.setId(genRandomId());
    questions.add(question);
    return question.getId();
  }
  
  private String genRandomId() {
    return new BigInteger(32, new SecureRandom()).toString();
  }

  public boolean deleteQuestion(String sid, String qid) {
    List<Question> questions = getAllQuestions(sid);
    if(null == questions) return false;

    return questions.removeIf(q -> q.getId().equalsIgnoreCase(qid));
  }

  public void updateQuestion(String sid, String qid, Question question) {
    deleteQuestion(sid, qid);
    question.setId(qid);
    getAllQuestions(sid).add(question);
  }
}
