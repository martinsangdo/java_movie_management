package com.java.spring.movie_management.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "employee")    //collection name
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String name;
}
