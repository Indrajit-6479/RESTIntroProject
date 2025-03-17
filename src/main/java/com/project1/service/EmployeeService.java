package com.project1.service;

import com.project1.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(String id);

    String deleteEmployeeById(String id);

    Employee updateEmployeeById(Employee employee);
}