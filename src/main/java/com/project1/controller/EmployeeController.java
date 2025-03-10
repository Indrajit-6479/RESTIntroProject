package com.project1.controller;

import com.project1.model.Employee;
import com.project1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/save")
    public Employee saveEmployees(@RequestBody Employee employee){
        return employeeService.save(employee);
    }
}
