package com.miltontest.springboot.restapi.hello;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

  @RequestMapping("hello")
  public String hello(){
    return "Hello world";
  }

  @RequestMapping("hellobean")
  public HelloBean helloWorldBean() {
    return new HelloBean("Hello World"); // converted to json
  }

  @RequestMapping("/hellobean/{name}/{msg}")
  public HelloBean helloWorldBean(@PathVariable String name, @PathVariable String msg) {
    return new HelloBean("Hello World, " + name + ", message: " + msg); // converted to json
  }
}
