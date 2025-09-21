package com.java.spring.movie_management.service;

import org.mapstruct.Mapper;

import com.java.spring.movie_management.model.Employee;
import com.java.spring.movie_management.model.EmployeeDTO;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO toDto(Employee employee);
    Employee toEntity(EmployeeDTO dto);
}
