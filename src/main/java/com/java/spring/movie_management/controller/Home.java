package com.java.spring.movie_management.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.java.spring.movie_management.service.ChatService;
import com.java.spring.movie_management.service.EmployeeService;
import com.java.spring.movie_management.service.ExternalApiService;
import com.java.spring.movie_management.service.LegalKnowledgeService;
import com.java.spring.movie_management.util.JwtUtil;

@Controller
public class Home {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ExternalApiService externalApiService;
    @Autowired
    ChatService chatService;
    @Autowired
    private SpringTemplateEngine templateEngine; // Inject Thymeleaf's template engine
    @Autowired
    private LegalKnowledgeService legalKnowledgeService;

    @GetMapping("/")
    @ResponseBody
    public String showHomePage(){
        return "Hi!";
    }

    @PostMapping("/welcome")
    @ResponseBody
    public ResponseEntity<String> sendWelcome(@RequestParam String email) {
        employeeService.sendWelcomeEmail(email);  // async
        return ResponseEntity.accepted().body("Email is being processed");
    }

    @GetMapping("/api/message")
    @ResponseBody
    public String showMessage(){
        return "This is a message from server!";
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Map<String, String> login() {
        Map<String, Object> claims = new HashMap<>();
        //put sample test data
        claims.put("user_id", "123456");
        claims.put("name", "Leo Messi");
        claims.put("email", "leo.messi@fifa.com");
        String token = JwtUtil.generateToken(claims);
        Map<String, String> res = new HashMap<>();
        res.put("token", token);
        return res;
    }
    //get & response chat results
    @PostMapping("/api/public/chat")
    @ResponseBody
    public ResponseEntity<String> askAIChat(@RequestParam String question) {
        if (Objects.nonNull(question)){
            String output = chatService.sendRequest2Gemini(question);
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        return new ResponseEntity<>("Empty query", HttpStatus.BAD_REQUEST);
    }
    //chain of thought
    @PostMapping("/api/public/chat-cot")
    @ResponseBody
    public ResponseEntity<String> askAIChatByCoT(@RequestParam String question) {
        if (Objects.nonNull(question)){
            //step 1: get topic
            String prompt1 = "You are a classifier. \n" + //
                                "Given the user's question, reply with only one topic from this list:\n" + //
                                "[\"immigration\", \"labour\", \"family\", \"normal_conversation\"].\n" + //
                                "\n" + //
                                "Do not explain your choice. \n" + //
                                "If unsure, choose the closest topic.\n" + //
                                "\n" + //
                                "Question: "+ question + //
                                "Topic:\n" + //
                                "";
            //step 2: get knowledge
            String topic = chatService.sendRequest2Gemini(prompt1);
            System.out.println(topic);
            if (topic.equalsIgnoreCase("normal_conversation")){
                //normal conversation
                String output = chatService.sendRequest2Gemini(question);
                return new ResponseEntity<>(output, HttpStatus.OK);
            }
            String knowledge = legalKnowledgeService.getKnowdgeByTopic(topic);
            if (knowledge.equalsIgnoreCase("")){
                //normal conversation
                String output = chatService.sendRequest2Gemini(question);
                return new ResponseEntity<>(output, HttpStatus.OK);
            }
            System.out.println(knowledge);
            //step 3: ask AI again with knowledge
            String prompt2 = "You are a legal assistant. \n" + //
                                "Use only the information from the knowledge below to answer the user’s question.\n" + //
                                "\n" + //
                                "Knowledge:\n" + //
                                "\"\"\"\n" + knowledge + //
                                "\"\"\"\n" + //
                                "\n" + //
                                "Question:\n" + //
                                question + //
                                "\n" + //
                                "If the knowledge does not contain enough information to answer, reply:\n" + //
                                "\"I'm sorry, I couldn't find relevant information in the knowledge base.\"\n";
            //
            String output = chatService.sendRequest2Gemini(prompt2);
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        return new ResponseEntity<>("Empty query", HttpStatus.BAD_REQUEST);
    }
    //
    @GetMapping("/ui/chat")
    public String chatPage() {
        return "chat";
    }
}
