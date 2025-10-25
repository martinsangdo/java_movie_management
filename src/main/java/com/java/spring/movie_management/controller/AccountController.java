package com.java.spring.movie_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.spring.movie_management.model.AccountDTO;
import com.java.spring.movie_management.service.AccountService;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/api/public/accounts")
    @ResponseBody
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        List<AccountDTO> allData = accountService.findAll();
        return new ResponseEntity<>(allData, HttpStatus.OK);
    }
}
