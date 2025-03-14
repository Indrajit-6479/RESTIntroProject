package com.project1.controller;

import com.project1.model.Employee;
import com.project1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employees/v2")
@RestController
public class EmployeeV2Controller {

    @Qualifier("employeeServiceV2Impl")
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/save")
    public Employee saveEmployees(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id){
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable String id){
        return employeeService.deleteEmployeeById(id);
    }
}
