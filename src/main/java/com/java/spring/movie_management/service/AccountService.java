package com.java.spring.movie_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public AccountDTO getDetail(String accountId){
        Account savedAccount = accountRepository.findByAccountId(accountId);
        return mapper.toDto(savedAccount);
    }

    public Optional<Account> getDetailByEmail(String email){
        Optional<Account> savedAccount = accountRepository.findByEmail(email);
        return savedAccount;
    }

    public AccountDTO createAccount(Account dto) {
        // Check for existing email
        Optional<Account> existing = accountRepository.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        Account account = new Account();
        account.setAccountId("A" + System.currentTimeMillis());
        account.setName(dto.getName());
        account.setEmail(dto.getEmail());
        account.setPhone(dto.getPhone());
        account.setPassword(dto.getPassword()); // TODO: hash before saving
        accountRepository.save(account);
        AccountDTO accountDTO = mapper.toDto(account);
        return accountDTO;
    }
}
