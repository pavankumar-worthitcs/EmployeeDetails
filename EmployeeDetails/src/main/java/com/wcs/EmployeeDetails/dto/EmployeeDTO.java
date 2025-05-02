package com.wcs.EmployeeDetails.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class EmployeeDTO {
    private String firstname;
    private String lastname;
    private String email;
}
