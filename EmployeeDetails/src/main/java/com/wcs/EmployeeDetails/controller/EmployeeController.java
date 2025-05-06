package com.wcs.EmployeeDetails.controller;

import com.wcs.EmployeeDetails.Entity.Employee;
import com.wcs.EmployeeDetails.dto.DTORequest;
import com.wcs.EmployeeDetails.dto.EmployeeDTO;
import com.wcs.EmployeeDetails.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("matchEmployee")
    public List<Employee> matchEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.matchEmployee(employeeDTO);
    }

    @PostMapping("/saveEmployee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/fetchEmployeeData/{attribute1}/{attribute2}")
    public List<Object[]> fetchEmployeeData(@PathVariable(name = "attribute1") String attribute1, @PathVariable(name = "attribute2") String attribute2) {

        return employeeService.fetchEmployeeData(attribute1, attribute2);

    }

    @GetMapping("/fetchEmployeeWithMatchedData/{data}/{page}/{size}")
    public ResponseEntity<List<DTORequest>> fetchEmployeeWithMatchedData(@PathVariable(name = "data") String data, @PathVariable(name = "page") int page, @PathVariable(name = "size") int size) {
        if (page == 0 || size == 0 || data.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<DTORequest> responses = employeeService.fetchEmployeeWithMatchedData(data, page, size);
        if (responses.isEmpty()){
            return ResponseEntity.ok(null);
        }
        else {
            return ResponseEntity.ok(responses);
        }
    }
}
