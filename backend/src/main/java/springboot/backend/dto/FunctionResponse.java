package springboot.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.backend.models.ModuleType;
import springboot.backend.models.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {

    private Long id;
    private ModuleType module;
    private String functionId;
    private String functionDescription;
    private Integer functionLevel;
    private Status status;
}
