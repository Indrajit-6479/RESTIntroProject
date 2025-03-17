package com.project1.service;

import com.project1.Exceptions.EmployeeNotFoundException;
import com.project1.entity.EmployeeEntity;
import com.project1.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final CommonMethodsService commonMethodsService;

    @Autowired
    public EmployeeServiceImpl(CommonMethodsService commonMethodsService) {
        this.commonMethodsService = commonMethodsService;
    }

    List<EmployeeEntity> employeesList = new ArrayList<>();

    @Override
    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeesList.add(employeeEntity);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeesList.stream().map(employeeEntity -> {
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeEntity, employee);
            return employee;
        }).toList();
    }

    @Override
    public Employee getEmployeeById(String id) {
        EmployeeEntity employeeEntity = employeesList.stream().filter(employee1 -> employee1.getEmployeeId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not present for id: " + id));

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeEntity, employee);
        return employee;
    }

    @Override
    public String deleteEmployeeById(String id) {
        Optional<EmployeeEntity> employee = employeesList.stream()
                .filter(employee1 -> employee1.getEmployeeId().equalsIgnoreCase(id))
                .findAny();
        if (employee.isPresent()) {
            employeesList.remove(employee.get());
            return String.format("Employee with id: %s is successfully deleted.", id);
        } else {
            throw new EmployeeNotFoundException("Employee is not present for id: " + id);
        }
    }

    @Override
    public Employee updateEmployeeById(Employee newEmployee) {
        OptionalInt matchingEmployeeIndex = IntStream.range(0, employeesList.size())
                .filter(i -> {
                    EmployeeEntity employee1 = employeesList.get(i);
                    return employee1.getEmployeeId().equalsIgnoreCase(newEmployee.getEmployeeId()) &&
                            employee1.getFirstName().equalsIgnoreCase(newEmployee.getFirstName()) &&
                            employee1.getLastName().equalsIgnoreCase(newEmployee.getLastName());
                }).findAny();
        if (matchingEmployeeIndex.isPresent()) {
            EmployeeEntity oldEmployee = employeesList.get(matchingEmployeeIndex.getAsInt());
            EmployeeEntity updatedEmployee = commonMethodsService.getEmployee(oldEmployee, newEmployee);
            employeesList.set(matchingEmployeeIndex.getAsInt(), updatedEmployee);
            BeanUtils.copyProperties(updatedEmployee, newEmployee);
            return newEmployee;
        } else {
            throw new EmployeeNotFoundException("Employee is not found with id: " + newEmployee.getEmployeeId() + " and name: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
        }
    }
}
