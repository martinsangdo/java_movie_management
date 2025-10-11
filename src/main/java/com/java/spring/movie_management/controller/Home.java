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

    @PostMapping("/api/public/chat")
    @ResponseBody
    public ResponseEntity<String> askAIChat(@RequestParam String question) {
        if (Objects.nonNull(question)){
            String output = chatService.sendRequest2Gemini(question);
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        return new ResponseEntity<>("Empty query", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ui/chat")
    public String chatPage() {
        return "chat";
    }
}
