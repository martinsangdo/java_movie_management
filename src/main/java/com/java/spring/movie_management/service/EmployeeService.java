package com.java.spring.movie_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.java.spring.movie_management.model.Employee;
import com.java.spring.movie_management.model.EmployeeDTO;
import com.java.spring.movie_management.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {
    private final EmployeeMapper mapper;
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDTO getEmployeeById(Long id) {
        // Employee entity = mapper.toEntity(dto);   // Convert DTO → Entity
        Employee savedEmployee = employeeRepository.findEmployeeById(id);
        EmployeeDTO employeeDTO = mapper.toDto(savedEmployee);  
        return employeeDTO;
    }

    @Async
    public void sendWelcomeEmail(String to) {
        log.debug("Sending email to: {}", to);
        //a very long process here
        //...
        log.info("Finish the function sendWelcomeEmail");
        System.out.println("Email sent to " + to);
    }

}
