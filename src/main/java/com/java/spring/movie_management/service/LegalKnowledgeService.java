package com.java.spring.movie_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.movie_management.model.LegalKnowledge;
import com.java.spring.movie_management.repository.LegalKnowledgeRepository;

@Service
public class LegalKnowledgeService {
    @Autowired
    LegalKnowledgeRepository legalKnowledgeRepository;

    public String getKnowdgeByTopic(String topic){
        LegalKnowledge legalKnowledge = legalKnowledgeRepository.findFirstByTopic(topic);
        if (legalKnowledge == null){
            return "";
        }
        return legalKnowledge.getKnowledge();
    }
}
