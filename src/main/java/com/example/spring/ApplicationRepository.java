package com.example.spring;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends MongoRepository<Todolist,String> {
   public List<Todolist> findAll();

}
