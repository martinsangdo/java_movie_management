package com.java.spring.movie_management.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "legal_knowledge")
@NoArgsConstructor
@AllArgsConstructor
public class LegalKnowledge {
    private String topic;
    private String knowledge;
}