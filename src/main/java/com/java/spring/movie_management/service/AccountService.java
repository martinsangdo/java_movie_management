package com.java.spring.movie_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.movie_management.model.Account;
import com.java.spring.movie_management.model.AccountDTO;
import com.java.spring.movie_management.model.Employee;
import com.java.spring.movie_management.model.EmployeeDTO;
import com.java.spring.movie_management.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private AccountMapper mapper;

    public List<AccountDTO> findAll(){
        List<Account> savedAccounts = accountRepository.findAll();
        List<AccountDTO> list = new ArrayList<>();
        for (Account account : savedAccounts){
            list.add(mapper.toDto(account));
        }
        return list;
    }
}
