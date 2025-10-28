package com.java.spring.movie_management.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.spring.movie_management.model.Author;


@Repository
public interface AuthorRepository extends MongoRepository<Author, Long> {
    List<Author> findByCountry(String country);

    @Query("{ '$or': [ { 'name': { $regex: ?0, $options: 'i' } }, { 'country': { $regex: ?0, $options: 'i' } } ] }")
    List<Author> searchByNameOrCountry(String keyword);
}
