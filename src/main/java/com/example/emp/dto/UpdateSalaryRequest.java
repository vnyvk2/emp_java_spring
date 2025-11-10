package com.example.emp.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSalaryRequest {
    @NotNull(message = "New salary is required")
    @DecimalMin(value = "0.01", message = "Salary must be greater than 0")
    private Double newSalary;
}