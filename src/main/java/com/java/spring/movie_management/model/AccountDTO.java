package com.java.spring.movie_management.model;

import lombok.Data;

@Data
public class AccountDTO {
    private String accountId;
    private String name;
    private String email;
    private String role;
}