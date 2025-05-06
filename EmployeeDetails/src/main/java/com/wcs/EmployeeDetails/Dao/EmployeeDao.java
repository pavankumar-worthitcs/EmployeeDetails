package com.wcs.EmployeeDetails.Dao;

import com.wcs.EmployeeDetails.Entity.Employee;
import com.wcs.EmployeeDetails.Entity.IdCard;
import com.wcs.EmployeeDetails.dto.DTORequest;
import com.wcs.EmployeeDetails.dto.EmployeeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
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
        Predicate firstnamePredicate = criteriaBuilder.like(root.get("firstname"), "%" + employee.getFirstname() + "%");
        Predicate lastnamePredicate = criteriaBuilder.like(root.get("lastname"), "%" + employee.getLastname() + "%");
        Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + employee.getEmail() + "%");
        Predicate finalPredicate = criteriaBuilder.or(firstnamePredicate, lastnamePredicate, emailPredicate);
        query.select(root).where(finalPredicate);
        return entityManager.createQuery(query).getResultList();

    }

    public List<Object[]> fetchEmployeeData(String attribute1, String attribute2) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, IdCard> idCard = employee.join("idCard", JoinType.LEFT);
        query.multiselect(employee.get(attribute1), idCard.get(attribute2));
          return entityManager.createQuery(query).getResultList();

    }

    public List<DTORequest> fetchEmployeeWithMatchedData(String data, int page, int size) {

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DTORequest> query = criteriaBuilder.createQuery(DTORequest.class);
            Root<Employee> employee = query.from(Employee.class);
            Join<Employee, IdCard> idCard = employee.join("idCard", JoinType.LEFT);
            Predicate finalPredicate = buildPredicate(criteriaBuilder,employee,idCard,data);

            query.select(buildDTOResult(criteriaBuilder,employee,idCard))
                    .where(finalPredicate).orderBy(criteriaBuilder.asc(employee.get("lastname")));

            return pagination(query,page,size);
    }


    public List<DTORequest> pagination(CriteriaQuery<DTORequest> query,int page,int size){
        return entityManager.createQuery(query)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size).getResultList();
    }


    private Selection<DTORequest> buildDTOResult(CriteriaBuilder criteriaBuilder, Root<Employee> employee, Join<Employee, IdCard> idCard) {
        return criteriaBuilder.construct(
                DTORequest.class,
                employee.get("id"),
                employee.get("firstname"),
                employee.get("lastname"),
                employee.get("email"),
                idCard.get("number")
        );
    }


    public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<Employee> employee, Join<Employee, IdCard> idCard,String data){
        String matchDataPattern = "%" + data.trim().toLowerCase() + "%";
        Predicate idPredicate = criteriaBuilder.like(criteriaBuilder.toString(employee.get("id")), matchDataPattern);
        Predicate firstnamePredicate = criteriaBuilder.like(criteriaBuilder.lower(employee.get("firstname")), matchDataPattern);
        Predicate lastNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(employee.get("lastname")), matchDataPattern);
        Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(employee.get("email")), matchDataPattern);
        Predicate numberPredicate = criteriaBuilder.like(criteriaBuilder.lower(idCard.get("number")), matchDataPattern);

        return  criteriaBuilder.or(idPredicate, firstnamePredicate, lastNamePredicate, emailPredicate, numberPredicate);
    }

}
