package springboot.backend.service;

import springboot.backend.dto.EmployeeDTO;

public interface EmployeeServie
{
    void createEmployee(EmployeeDTO employeeDTO);
}