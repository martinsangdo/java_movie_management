package com.java.spring.movie_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.spring.movie_management.model.Account;
import com.java.spring.movie_management.model.AccountDTO;
import com.java.spring.movie_management.model.Transaction;
import com.java.spring.movie_management.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

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

    @Operation(
        summary = "Get list of transactions for an account",
        description = "Retrieves paginated transactions for a specific account within a date range.",
        parameters = {
            @Parameter(name = "accountId", description = "Account ID", example = "A1001"),
            @Parameter(name = "from", description = "Start date (yyyy-MM-dd)", example = "2025-10-01"),
            @Parameter(name = "to", description = "End date (yyyy-MM-dd)", example = "2025-10-25"),
            @Parameter(name = "page", description = "Page number", example = "1"),
            @Parameter(name = "limit", description = "Page size", example = "5")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Transactions retrieved successfully",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n  \"page\": 1,\n  \"limit\": 2,\n  \"total\": 5,\n  \"transactions\": [\n    {\n      \"id\": \"t111\",\n      \"transactionType\": \"DEPOSIT\",\n      \"amount\": 1000000,\n      \"description\": \"Initial deposit\",\n      \"timestamp\": \"2025-10-19T09:30:00\"\n    },\n    {\n      \"id\": \"t112\",\n      \"transactionType\": \"DEPOSIT\",\n      \"amount\": 2000000,\n      \"description\": \"Deposit via ATM\",\n      \"timestamp\": \"2025-10-20T13:00:00\"\n    }\n  ]\n}")
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n  \"error\": \"ValidationError\",\n  \"message\": \"Account ID is invalid\"\n}")
                )
            )
        }
    )
    @GetMapping("/api/v1/accounts/{accountId}/transactions")
    public ResponseEntity<Object> getTransactions(@PathVariable String accountId,
                                @RequestParam String from, @RequestParam String to,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "5") Integer limit){
        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }

    @PostMapping("/api/public/account")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody Account dto) {
        AccountDTO saved = accountService.createAccount(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
