package com.java.spring.movie_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.env.Environment;

@Service
public class ChatService {
    @Autowired
    ExternalApiService externalApiService;
    @Autowired
    private Environment env;
    final String GEMINI_API_URI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=";
    //
    JsonNode buildGeminiRequestFormat(String query) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode textNode = mapper.createObjectNode();
        textNode.put("text", query);
        ArrayNode partsArray = mapper.createArrayNode();
        partsArray.add(textNode);
        ObjectNode contentsNode = mapper.createObjectNode();
        contentsNode.set("parts", partsArray);
        ObjectNode root = mapper.createObjectNode();
        root.set("contents", contentsNode);
        String jsonBody = mapper.writeValueAsString(root);
        String data = externalApiService.sendPostRequest(GEMINI_API_URI + env.getProperty("gemini_api_key"), jsonBody);
        JsonNode jsonData = mapper.readTree(data);
        return jsonData;
    }
    //
    public String sendRequest2Gemini(String query){
        try {
            JsonNode jsonData = buildGeminiRequestFormat(query);
            // Path: candidates[0].content.parts[0].text
            System.out.println(jsonData);
            String text = jsonData.path("candidates")
                          .get(0)
                          .path("content")
                          .path("parts")
                          .get(0)
                          .path("text")
                          .asText();
            return text;
        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
