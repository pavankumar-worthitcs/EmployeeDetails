package com.wcs.EmployeeDetails.Dao;

import com.wcs.EmployeeDetails.Entity.Employee;
import com.wcs.EmployeeDetails.Entity.IdCard;
import com.wcs.EmployeeDetails.dto.EmployeeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao {


    private final EntityManager entityManager;

    public EmployeeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Employee> searchEmployeeByDetails(EmployeeDTO employee) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        Predicate firstnamePredicate = criteriaBuilder.like(root.get("firstname"), "%"+employee.getFirstname()+"%");

        Predicate lastnamePredicate = criteriaBuilder.like(root.get("lastname"), "%" + employee.getLastname() + "%");

        Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + employee.getEmail() + "%");

        Predicate finalPredicate = criteriaBuilder.or(firstnamePredicate, lastnamePredicate,emailPredicate);

        query.select(root).where(finalPredicate);

         return entityManager.createQuery(query).getResultList();

    }

    public List<Object[]> fetchEmployeeData(String attribute1,String attribute2){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<Employee> employee =query.from(Employee.class);

        Join<Employee, IdCard> idCard =employee.join("idCard", JoinType.LEFT);

        query.multiselect(employee.get(attribute1),idCard.get(attribute2));
        return entityManager.createQuery(query).getResultList();

    }

}
