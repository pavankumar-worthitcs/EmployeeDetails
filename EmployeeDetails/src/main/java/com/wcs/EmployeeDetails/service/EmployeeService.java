package com.wcs.EmployeeDetails.service;
import com.wcs.EmployeeDetails.Dao.EmployeeDao;
import com.wcs.EmployeeDetails.Entity.Employee;
import com.wcs.EmployeeDetails.Repository.EmployeeRepository;
import com.wcs.EmployeeDetails.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

}
