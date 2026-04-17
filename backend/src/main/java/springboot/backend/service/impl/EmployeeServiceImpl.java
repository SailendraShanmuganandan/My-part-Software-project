package springboot.backend.service.impl;

import springboot.backend.dao.EmployeeRepository;
import springboot.backend.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import springboot.backend.service.EmployeeServie;


//@Slf4j - > log message
@Service
public class EmployeeServiceImpl implements EmployeeServie {

    private final EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createEmployee(EmployeeDTO employeeDTO) {
        Long employee_id=employeeRepository.save(employeeDTO.getEntity()).getUserId();
        //return employee created {emp_id}
    }
}