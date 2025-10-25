package com.java.spring.movie_management.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String accountId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Integer credit_score;
    private String reference_code;
}