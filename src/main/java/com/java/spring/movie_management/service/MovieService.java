package com.java.spring.movie_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.movie_management.model.Movie;
import com.java.spring.movie_management.repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }
    
}
