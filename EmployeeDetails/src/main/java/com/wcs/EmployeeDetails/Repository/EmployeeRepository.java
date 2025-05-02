package com.wcs.EmployeeDetails.Repository;

import com.wcs.EmployeeDetails.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
