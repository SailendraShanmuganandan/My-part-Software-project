package springboot.backend.service;

import springboot.backend.dto.EmployeeDTO;
import springboot.backend.dto.UserSearchDTO;
import springboot.backend.models.UserAccount;

import java.util.List;

public interface UserSearchService {
    List<UserAccount> searchUser(UserSearchDTO request);
}
