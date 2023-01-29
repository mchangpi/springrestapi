package com.miltontest.springboot.restapi.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailCmdLineRunner implements CommandLineRunner {

  private Logger logger = LoggerFactory.getLogger(getClass());
  private UserDetailRepository repo;
  
  @Autowired
  public UserDetailCmdLineRunner(UserDetailRepository repo) {
    super();
    this.repo = repo;
  }

  @Override
  public void run(String... args) throws Exception {
    logger.debug(Arrays.toString(args));
    //repo.save(new UserDetail("milton", "admin "));
    //repo.save(new UserDetail("test", "admin"));
    
    List<UserDetail> users = repo.findByRole("admin");
    users.forEach(u -> logger.info(u.toString()));
  }

}
