package springboot.backend.dto;

import lombok.Data;
import springboot.backend.models.Status;

@Data
public class RoleRequest {

    private Long locationId;      // 0 = ALL LOCATION
    private String roleCode;
    private String roleDescription;
    private Status status;
}
