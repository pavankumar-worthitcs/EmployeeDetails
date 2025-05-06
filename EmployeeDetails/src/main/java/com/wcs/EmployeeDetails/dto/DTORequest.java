package com.wcs.EmployeeDetails.dto;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTORequest {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String number;
}
