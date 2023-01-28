package com.miltontest.springboot.restapi.survey;

import java.util.List;

public class Question {
  private String id;
  private String description;
  private List<String> options;
  private String answer;
  
  public Question() {
  }

  public Question(String id, String description, List<String> options, String answer) {
    super();
    this.id = id;
    this.description = description;
    this.options = options;
    this.answer = answer;
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getOptions() {
    return options;
  }

  public String getAnswer() {
    return answer;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  @Override
  public String toString() {
    return "Question [description=" + description + ", options=" + options + ", answer=" + answer + "]";
  }
  
}
