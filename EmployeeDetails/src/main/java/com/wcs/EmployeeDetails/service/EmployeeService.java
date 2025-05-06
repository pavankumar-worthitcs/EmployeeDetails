package com.wcs.EmployeeDetails.service;
import com.wcs.EmployeeDetails.Dao.EmployeeDao;
import com.wcs.EmployeeDetails.Entity.Employee;
import com.wcs.EmployeeDetails.Entity.IdCard;
import com.wcs.EmployeeDetails.Repository.EmployeeRepository;
import com.wcs.EmployeeDetails.dto.DTORequest;
import com.wcs.EmployeeDetails.dto.EmployeeDTO;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> matchEmployee(EmployeeDTO employeeDTO){

        return employeeDao.searchEmployeeByDetails(employeeDTO);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Object[]> fetchEmployeeData(String attribute1,String attribute2){

      return employeeDao.fetchEmployeeData(attribute1,attribute2);

    }

    public List<DTORequest> fetchEmployeeWithMatchedData(String data, int page, int size) {
        return employeeDao.fetchEmployeeWithMatchedData(data,page,size);
    }



}
