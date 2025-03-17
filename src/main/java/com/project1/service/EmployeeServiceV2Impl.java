package com.project1.service;

import com.project1.Exceptions.EmployeeNotFoundException;
import com.project1.entity.EmployeeEntity;
import com.project1.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceV2Impl implements EmployeeService {
    final private EmployeeServiceV2ImplDependencies dependencies;

    @Autowired
    public EmployeeServiceV2Impl(EmployeeServiceV2ImplDependencies dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        dependencies.getEmployeeRepository().save(employeeEntity);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntity = dependencies.getEmployeeRepository().findAll();
        return employeeEntity.stream()
                .map(employeeEntity1 -> {
                    Employee employee = new Employee();
                    BeanUtils.copyProperties(employeeEntity1, employee);
                    return employee;
                }).toList();
    }

    @Override
    public Employee getEmployeeById(String id) {
        Optional<EmployeeEntity> employeeEntity = dependencies.getEmployeeRepository().findById(id);
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
        Optional<EmployeeEntity> employee = dependencies.getEmployeeRepository().findById(id);
        if (employee.isPresent()) {
            dependencies.getEmployeeRepository().deleteById(id);
            return String.format("Delete the employee with id %s in database", id);
        } else {
            throw new EmployeeNotFoundException("Employee is not found with id: " + id);
        }
    }

    @Override
    public Employee updateEmployeeById(Employee newEmployee) {
        String employeeId = newEmployee.getEmployeeId();
        Optional<EmployeeEntity> oldEmployee = dependencies.getEmployeeRepository().findById(employeeId);
        if (oldEmployee.isPresent()) {
            EmployeeEntity updatedEmployee = dependencies.getCommonMethodsService().getEmployee(oldEmployee.get(), newEmployee);
            dependencies.getEmployeeRepository().save(updatedEmployee);
            BeanUtils.copyProperties(updatedEmployee, newEmployee);
            return newEmployee;
        } else {
            throw new EmployeeNotFoundException("Employee is not found with id: " + newEmployee.getEmployeeId() + " and name: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
        }

    }
}
