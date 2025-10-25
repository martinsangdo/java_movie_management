package com.java.spring.movie_management.model;

import java.sql.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "transaction")    //collection name
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String id;
    @Field("transaction_type")
    private String transactionType;
    private Long amount;
    private String description;
    private Date timestamp;
}
  