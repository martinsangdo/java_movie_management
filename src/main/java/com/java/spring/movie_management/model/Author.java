package com.java.spring.movie_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document("authors")
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    private Long _id;
    
    private String name;
    private String country;
    @Field("birth_year")
    private Integer birthYear;
    private String genre;

    @Field("is_active")
    private Boolean isActive = false; // new field

}
