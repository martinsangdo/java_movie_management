package com.java.spring.movie_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.spring.movie_management.model.AccountDTO;
import com.java.spring.movie_management.model.Transaction;
import com.java.spring.movie_management.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/api/public/accounts")
    @ResponseBody
    @Operation(summary = "Get all accounts", description = "Fetches all account records with limited fields (name, email, accountId).")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        List<AccountDTO> allData = accountService.findAll();
        return new ResponseEntity<>(allData, HttpStatus.OK);
    }

    @GetMapping("/api/public/accounts/{id}")
    @ResponseBody
    public ResponseEntity<AccountDTO> getAccountDetail(@PathVariable String id){
        AccountDTO detail = accountService.getDetail(id);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @PostMapping("/api/v1/accounts/{accountId}/transaction")
    @ResponseBody
    @Operation(
            summary = "Create a new transaction for an account",
            description = "Creates a transaction record under a given account ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error occurred",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                  "error": "ValidationError",
                                  "message": "Amount must be greater than 0",
                                  "fields": ["amount"]
                                }
                            """))
            )
    })
    public ResponseEntity<Transaction> createNewTransaction(@PathVariable String accountId){
        return new ResponseEntity<>(new Transaction(), HttpStatus.OK);
    }
}
