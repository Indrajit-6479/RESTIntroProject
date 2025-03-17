package com.project1.service;

import com.project1.entity.EmployeeEntity;
import com.project1.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommonMethodsService {
    EmployeeEntity getEmployee(EmployeeEntity oldEmployee, Employee newEmployee) {
        if (!Objects.isNull(newEmployee.getEmailId()) && !newEmployee.getEmailId().isEmpty()) {
            oldEmployee.setEmailId(newEmployee.getEmailId());
        }
        if (!Objects.isNull(newEmployee.getDepartment()) && !newEmployee.getDepartment().isEmpty()) {
            oldEmployee.setDepartment(newEmployee.getDepartment());
        }
        return oldEmployee;
    }
}
