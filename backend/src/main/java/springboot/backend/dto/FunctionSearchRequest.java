package springboot.backend.dto;

import lombok.Data;
import springboot.backend.models.ModuleType;
import springboot.backend.models.Status;

@Data
public class FunctionSearchRequest {

    private ModuleType module;
    private String functionId;
    private String functionDescription;
    private Integer functionLevel;
    private Status status;
}
