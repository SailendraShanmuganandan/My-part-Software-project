package springboot.backend.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.backend.models.Employee;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private String title;
    private String firstName;
    private String middleName;
    private String surName;
    private String fullName;
    private String gender;
    private String maritalStatus;

    private LocalDate dateOfBirth;
    private String nicNumber;

    //Contact and Employment detail
    private String qualification;
    private LocalDate recruitmentDate;
    private LocalDate appointmentDate;
    private String telephoneNumber;
    private String mobileNumber;
    private String epfNumber;
    private String emailAddress;
    private String designation;

    //account and status
    private String permanentAddress;
    private String residenceAddress;
    private String officeAddress;

    @JsonIgnore //do not include this to the frontend (Because Jackson only detects getters starting with get)
    public Employee getEntity() {
        Employee employee = new Employee();
        employee.setUserId(null);//this line should be handle
        BeanUtils.copyProperties(this, employee);
        return employee;
    }

}
