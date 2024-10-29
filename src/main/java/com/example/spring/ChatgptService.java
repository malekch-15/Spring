package com.example.spring;

import com.example.spring.ChatGpt.chatgpMessage;
import com.example.spring.ChatGpt.chatgptformat;
import com.example.spring.ChatGpt.chatgptrequest;
import com.example.spring.ChatGpt.chatgptrespons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChatgptService {
    private final RestClient restclient;
    private final ObjectMapper objectMapper;
    @Value("${app.chatgpt.api.token}")
    private String token;

    public ChatgptService(RestClient.Builder restclient, ObjectMapper objectMapper) {
     this.restclient = restclient.build();
        this.objectMapper = objectMapper;
    }
    public Todolist checkdescription(Todolist todo) throws JsonProcessingException {
        chatgptrespons body =restclient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new chatgptrequest("gpt-4o-mini"
                        , List.of(new chatgpMessage("user","check a text for any spelling mistakes in this todo:" + todo
                        + ". create JSON Object with this shema :{ description:String,status: String}"))
                        , new chatgptformat("json_object")))
                                .retrieve().body(chatgptrespons.class);
        System.out.println(body.choices().get(0).message().content());
        Todolist todolist1 =  objectMapper.readValue(body.choices().get(0).message().content(), Todolist.class);
        System.out.println(todolist1);
        return todolist1;

    }

}
