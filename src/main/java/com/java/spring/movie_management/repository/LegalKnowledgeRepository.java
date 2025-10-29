package com.java.spring.movie_management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.movie_management.model.LegalKnowledge;

@Repository
public interface LegalKnowledgeRepository extends MongoRepository<LegalKnowledge, String> {
    LegalKnowledge findFirstByTopic(String topic);
}
