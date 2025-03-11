package com.project1.service;

import com.project1.Exceptions.EmployeeNotFoundException;
import com.project1.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    List<Employee> employeesList = new ArrayList<>();

    @Override
    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        employeesList.add(employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeesList;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeesList.stream().filter(employee1 -> employee1.getEmployeeId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not present for id " + id));
    }
}
