package com.java.spring.movie_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.spring.movie_management.service.EmployeeService;

@Controller
public class Home {
    @Autowired
    EmployeeService employeeService;

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
}
