package com.miltontest.springboot.restapi.user;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDetailRestRepository extends PagingAndSortingRepository<UserDetail, Long>{
  List<UserDetail> findByRole(String role);
}
