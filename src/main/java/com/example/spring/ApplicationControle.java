package com.example.spring;

import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController//is it ein controller die nimt Http
@RequestMapping("/api/todo")

public class ApplicationControle {
    Todolist todolist;
    public final ServicetoDO repository;

    public ApplicationControle(ServicetoDO repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Todolist> gettodo(){
       return repository.findAll();
    }
//   @PostMapping
//    public void addtodolist(@RequestBody Todolist todolist){
//        repository.save(todolist);
//   }
   @PutMapping("/{id}")
    public void updated (@PathVariable String id ,@RequestBody Todolist todolistupdated){
        repository.todoupdated(todolistupdated,id);
   }
   @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        repository.deleteByid(id);
    }
    @GetMapping("/{id}")
    public Optional<Todolist> getById(@PathVariable String id){
        System.out.println("the id "+id);
       return repository.findbyid(id);
    }

    @PostMapping
    public ResponseEntity<Todolist> createList(@RequestBody Todolist todolist) {
       Todolist todolist1 = repository.save(todolist);

        // Return 201 Created with the location of the newly created resource
        return ResponseEntity
                .created(URI.create("/todolist/" + todolist1.id()))  // Set Location header
                .body(todolist);  // Return the created character in the body
    }

}

