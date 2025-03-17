package com.project1.service;

import com.project1.repository.EmployeeRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmployeeServiceV2ImplDependencies {
    private final EmployeeRepository employeeRepository;
    private final CommonMethodsService commonMethodsService;

    @Autowired
    public EmployeeServiceV2ImplDependencies(EmployeeRepository employeeRepository, CommonMethodsService commonMethodsService) {
        this.employeeRepository = employeeRepository;
        this.commonMethodsService = commonMethodsService;
    }
}
