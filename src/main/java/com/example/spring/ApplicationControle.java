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

    public final ServicetoDO repository;

    private final UndoService undoService;

    public ApplicationControle(ServicetoDO repository, UndoService undoService) {
        this.repository = repository;
        this.undoService = undoService;
    }

    @GetMapping
    public List<Todolist> gettodo(){
       return repository.findAll();
    }
   @PostMapping
    public void addtodolist(@RequestBody Todolist todolist){
       undoService.recordAction(new Action("CREATE", todolist));
        repository.save(todolist);

   }
    @PutMapping("/{id}")
    public void updated (@PathVariable String id ,@RequestBody Todolist todolistupdated) {
        Optional<Todolist> existingTodolist = repository.findbyid(id);
        if (existingTodolist.isPresent()) {
            // Record the previous state before the update
            undoService.recordAction(new Action("UPDATE", existingTodolist.get()));

            // Perform the update
            repository.todoupdated(todolistupdated, id);
        }
    }
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
       Optional<Todolist> todolist = repository.findbyid(id);
       if (todolist.isPresent()) {
           undoService.recordAction(new Action("DELETE", todolist.get()));
           repository.deleteByid(id);
           return ResponseEntity.noContent().build();
       } else {
           return ResponseEntity.notFound().build();

       }

    }
    @GetMapping("/{id}")
    public Optional<Todolist> getById(@PathVariable String id){
        System.out.println("the id "+id);
       return repository.findbyid(id);
    }

//    @PostMapping
//    public ResponseEntity<Todolist> createList(@RequestBody Todolist todolist) {
//       Todolist todolist1 = repository.save(todolist);
//
//        // Return 201 Created with the location of the newly created resource
//        return ResponseEntity
//                .created(URI.create("/todolist/" + todolist1.id()))  // Set Location header
//                .body(todolist);  // Return the created character in the body
//    }

    @DeleteMapping("/undo")
    public ResponseEntity<String> undoLastChange(String id) {
        Action lastAction = undoService.undoLastAction();
        if (lastAction == null) {
            return ResponseEntity.badRequest().body("No actions to undo");
        }

        switch (lastAction.type()) {
            case "CREATE":
                repository.deleteByid(lastAction.todolist().id());
                break;
            case "UPDATE":
                // Handle undoing update if necessary
                repository.save(lastAction.todolist());
                break;
            case "DELETE":
               repository.save(lastAction.todolist());
                break;
            default:
                return ResponseEntity.badRequest().body("Unknown action type");
        }

        return ResponseEntity.ok("Last action undone successfully");
    }

    }




