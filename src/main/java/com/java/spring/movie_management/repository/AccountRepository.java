package com.java.spring.movie_management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.movie_management.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    List<Account> findAll();
    Account findByAccountId(String accountId);
}
