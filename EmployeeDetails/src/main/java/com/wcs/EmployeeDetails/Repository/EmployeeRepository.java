package com.wcs.EmployeeDetails.Repository;

import com.wcs.EmployeeDetails.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query(value = "CALL manageEmployee(:option_type, :emp_id, :emp_firstname, :emp_lastname, :emp_email, @result_msg);", nativeQuery = true)
    void manageEmployee(
            @Param("op_type")Integer option_type,
            @Param("emp_id") Integer emp_id,
            @Param("emp_firstname") String emp_firstname,
            @Param("emp_lastname") String emp_lastname,
            @Param("emp_email") String emp_email);

    @Query(value = "SELECT @result_msg", nativeQuery = true)
    String getResultMessage();
}
