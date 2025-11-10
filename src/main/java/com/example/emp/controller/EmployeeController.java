package com.example.emp.controller;

import com.example.emp.dto.CreateEmployeeRequest;
import com.example.emp.dto.EmployeeResponse;
import com.example.emp.dto.UpdateSalaryRequest;
import com.example.emp.mapper.EmployeeMapper;
import com.example.emp.model.Employee;
import com.example.emp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;

    public EmployeeController(EmployeeService service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody CreateEmployeeRequest request) {
        Employee employee = mapper.toEntity(request);
        Employee created = service.create(employee);
        return ResponseEntity
                .created(URI.create("/employees/" + created.getId()))
                .body(mapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        return ResponseEntity.ok(mapper.toResponseList(service.getAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/salary")
    public ResponseEntity<EmployeeResponse> updateSalary(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSalaryRequest request) {
        Employee updated = service.updateSalary(id, request.getNewSalary());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @GetMapping("/average-salary")
    public ResponseEntity<Map<String, Double>> averageSalaryPerDept() {
        return ResponseEntity.ok(service.averageSalaryPerDepartment());
    }

    @GetMapping("/highest-salary")
    public ResponseEntity<EmployeeResponse> highestSalary() {
        return ResponseEntity.ok(mapper.toResponse(service.highestPaidEmployee()));
    }

    @GetMapping("/above-average")
    public ResponseEntity<List<EmployeeResponse>> aboveAverage() {
        return ResponseEntity.ok(mapper.toResponseList(service.employeesAboveAverage()));
    }
}