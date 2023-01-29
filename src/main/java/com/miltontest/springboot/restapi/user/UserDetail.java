package com.miltontest.springboot.restapi.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserDetail {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String role;
  
  public UserDetail() {

  }

  public UserDetail(String name, String role) {
    super();
    this.name = name;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getRole() {
    return role;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "UserDetail [name=" + name + ", role=" + role + "]";
  }
  
}
