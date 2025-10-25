package com.java.spring.movie_management.service;

import org.mapstruct.Mapper;

import com.java.spring.movie_management.model.Account;
import com.java.spring.movie_management.model.AccountDTO;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toDto(Account account);
}
