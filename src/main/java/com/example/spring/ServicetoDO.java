package com.example.spring;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Builder

public class ServicetoDO {

        private final ApplicationRepository repository;
    private final UndoService undoService;
    private final ChatgptService chatgptService;

    public ServicetoDO (ApplicationRepository repository, UndoService undoService, ChatgptService chatgptService) {
            this.repository = repository;
        this.undoService = undoService;
        this.chatgptService = chatgptService;
    }

        public List<Todolist> findAll() {
            return repository.findAll();
        }

        public Todolist save(Todolist todolist) throws JsonProcessingException {
         Todolist savedtodolist= Todolist.builder().id(todolist.id()).description(todolist.description()).status(todolist.status()).build();

         return repository.save( chatgptService.checkdescription(savedtodolist));
        }
        public Optional <Todolist> findbyid(String id){

        return repository.findById(id);
        }
        public void todoupdated (Todolist updatedtodolist,String id){
            repository.findById(id).ifPresent(todolist ->{
                Todolist updatedlist=todolist.withDescription(updatedtodolist.description())
                        .withStatus(updatedtodolist.status());
                repository.save(updatedlist);
            });

            }
    public void deleteByid (String id) {
        repository.deleteById(id);

    }
}


