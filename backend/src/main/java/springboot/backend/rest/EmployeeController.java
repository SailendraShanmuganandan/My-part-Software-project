package springboot.backend.rest;

import springboot.backend.dto.EmployeeDTO;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.backend.service.EmployeeServie;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeServie employeeServie;
    public EmployeeController(EmployeeServie employeeServie) {
        this.employeeServie = employeeServie;
    }


    @PostMapping("/create")
    public ResponseEntity<@NonNull String> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeServie.createEmployee(employeeDTO);
        return ResponseEntity.ok("Employee created successfully");
    }
}
