package springboot.backend.service;

import springboot.backend.dto.UserLimitDTO;
import springboot.backend.models.UserLimit;

import java.util.List;

public interface UserLimitService {


    // updated version can create

  void addUserLimit(List<UserLimitDTO> userLimits);
  void updateUserLimit(List<UserLimitDTO> userLimits);
  List<UserLimit> findUserLimit(Long userId);
  void deleteUserLimit(Long userLimitId);

}
