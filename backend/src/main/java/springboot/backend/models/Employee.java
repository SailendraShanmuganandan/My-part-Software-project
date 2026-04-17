package springboot.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String title;
    private String firstName;
    private String middleName;
    private String surName;
    private String fullName;
    private String gender;
    private String maritalStatus;


    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String nicNumber;

    //Contact and Employment detail

    private String qualification;
    private LocalDate recruitmentDate;
    private LocalDate appointmentDate;
    private String telephoneNumber;
    private String mobileNumber;
    @Column(unique = true)
    private String epfNumber;
    private String emailAddress;
    private String designation;
//private String employeeCadre
    //account and status

    @Column(length = 500)
    private String permanentAddress;
    @Column(length = 500)
    private String residenceAddress;
    @Column(length = 500)
    private String officeAddress;

    //private employee_status
    @OneToOne
    @JsonBackReference
    private UserAccount userAccount;




}
