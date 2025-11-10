package com.example.emp.mapper;

import com.example.emp.dto.CreateEmployeeRequest;
import com.example.emp.dto.EmployeeResponse;
import com.example.emp.model.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public Employee toEntity(CreateEmployeeRequest request) {
        return new Employee(null, request.getName(), 
                          request.getSalary(), request.getDepartment());
    }

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(employee.getId(), employee.getName(),
                                   employee.getSalary(), employee.getDepartment());
    }

    public List<EmployeeResponse> toResponseList(List<Employee> employees) {
        return employees.stream()
                       .map(this::toResponse)
                       .collect(Collectors.toList());
    }
}