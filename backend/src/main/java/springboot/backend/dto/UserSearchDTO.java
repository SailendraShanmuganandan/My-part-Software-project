package springboot.backend.dto;

import lombok.Data;

@Data
public class UserSearchDTO {
    private String location;
    private String userId;
    private String epfNumber;
    private String idNumber;
}
