package com.example.emp.service;

import com.example.emp.exception.ResourceNotFoundException;
import com.example.emp.model.Employee;
import com.example.emp.repository.InMemoryEmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final InMemoryEmployeeRepository repo;

    public EmployeeService(InMemoryEmployeeRepository repo) {
        this.repo = repo;
        seedData();
    }

    private void seedData() {
        if (repo.findAll().isEmpty()) {
            repo.save(new Employee(null, "Asha", 30000.0, "HR"));
            repo.save(new Employee(null, "Ravi", 45000.0, "Engineering"));
            repo.save(new Employee(null, "Priya", 52000.0, "Engineering"));
            repo.save(new Employee(null, "Karan", 28000.0, "Support"));
        }
    }

    public Employee create(Employee e) {
        e.setId(null);
        return repo.save(e);
    }

    public Employee getById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found with id " + id));
    }

    public List<Employee> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        if (repo.findById(id).isEmpty())
            throw new ResourceNotFoundException("Employee not found with id " + id);
        repo.deleteById(id);
    }

    public Employee updateSalary(Long id, Double newSalary) {
        Employee e = getById(id);
        e.setSalary(newSalary);
        return repo.save(e);
    }

    public Map<String, Double> averageSalaryPerDepartment() {
        return getAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)));
    }

    public Employee highestPaidEmployee() {
        return getAll().stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> new ResourceNotFoundException("No employees available"));
    }

    public List<Employee> employeesAboveAverage() {
        Map<String, Double> avgByDept = averageSalaryPerDepartment();
        return getAll().stream()
                .filter(e -> {
                    Double deptAvg = avgByDept.get(e.getDepartment());
                    return deptAvg != null && e.getSalary() > deptAvg;
                })
                .collect(Collectors.toList());
    }


}