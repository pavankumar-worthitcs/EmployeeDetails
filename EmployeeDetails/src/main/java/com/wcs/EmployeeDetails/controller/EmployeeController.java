package com.wcs.EmployeeDetails.controller;

import com.wcs.EmployeeDetails.Entity.Employee;
import com.wcs.EmployeeDetails.dto.EmployeeDTO;
import com.wcs.EmployeeDetails.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("matchEmployee")
    public List<Employee> matchEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.matchEmployee(employeeDTO);
    }

    @PostMapping("/saveEmployee")
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }


}
