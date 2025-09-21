package com.java.spring.movie_management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.movie_management.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findAll();
}
