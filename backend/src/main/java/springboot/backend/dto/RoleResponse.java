package springboot.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.backend.models.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private Long id;
    private String locationName;
    private String roleCode;
    private String roleDescription;
    private Status status;
}
