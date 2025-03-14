package com.project1.service;

import com.project1.Exceptions.EmployeeNotFoundException;
import com.project1.entity.EmployeeEntity;
import com.project1.model.Employee;
import com.project1.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceV2Impl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeRepository.save(employeeEntity);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntity = employeeRepository.findAll();
        return employeeEntity.stream()
                .map(employeeEntity1 -> {
                    Employee employee = new Employee();
                    BeanUtils.copyProperties(employeeEntity1, employee);
                    return employee;
                }).toList();
    }

    @Override
    public Employee getEmployeeById(String id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        if (employeeEntity.isPresent()) {
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeEntity.get(), employee);
            return employee;
        } else {
            throw new EmployeeNotFoundException("Employee is not present with id " + id);
        }
    }

    @Override
    public String deleteEmployeeById(String id) {
        return "";
    }
}
