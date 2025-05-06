package com.wcs.EmployeeDetails.controller;

import com.wcs.EmployeeDetails.Entity.Employee;
import com.wcs.EmployeeDetails.Entity.IdCard;
import com.wcs.EmployeeDetails.dto.DTORequest;
import com.wcs.EmployeeDetails.dto.EmployeeDTO;
import com.wcs.EmployeeDetails.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @PutMapping("/updateEmployeeDetails")
    public ResponseEntity<String> updateEmployeeDetails(@RequestParam Long employeeId,@RequestParam String newEmail){
        Integer status =employeeService.updateEmployeeDetails(employeeId,newEmail);
        if(status != 0){
         return  ResponseEntity.ok("Employee details successfully updated ");
        }
        return ResponseEntity.ok("Employee Id not found, please provide valid Id");
    }

    @DeleteMapping("deleteEmployeeById/{id}")
    public ResponseEntity<Map<String,String>> deleteEmployeeById(@PathVariable(name = "id") Long employeeId){
        Integer status =employeeService.deleteEmployeeById(employeeId);
        Map<String,String> response = new HashMap<>();
        if(status != 0){
            response.put("message", "Employee deleted Successfully");

        }
        else {
            response.put("message", "Employee Id not found, please provide valid Id");
        }
        return ResponseEntity.ok(response);
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

    @PutMapping("/createNewIdCardForEmployee/{id}")
    public ResponseEntity<Map<String,String>> createNewIdCardForEmployee(@PathVariable(name = "id") Long employeeId,@RequestBody IdCard newIdCard){
         Employee dbEmployee = employeeService.createNewIdCardForEmployee(employeeId,newIdCard);
        Map<String,String> response = new HashMap<>();
         if(dbEmployee!= null){
             response.put("message", "Id card created Successfully For Employee");
         }
         else{
             response.put("message", "Employee Id not found, please provide valid Id");
         }
         return ResponseEntity.ok(response);
    }
}
