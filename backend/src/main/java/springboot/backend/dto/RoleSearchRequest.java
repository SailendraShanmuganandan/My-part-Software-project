package springboot.backend.dto;

import lombok.Data;

@Data
public class RoleSearchRequest {

    private Long locationId;
    private String roleCode;
    private String roleDescription;
}
