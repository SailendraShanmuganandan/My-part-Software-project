package springboot.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.backend.dto.UserLimitDTO;
import springboot.backend.models.UserLimit;
import springboot.backend.service.UserLimitService;

import java.util.List;

@RestController
@RequestMapping("/userLimit")
public class UserLimitController {

    public UserLimitService userLimitService;
    @Autowired
    public void setUserLimitService(UserLimitService userLimitService) {
        this.userLimitService = userLimitService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUserLimit(@RequestBody List<UserLimitDTO> userLimits) {
        userLimitService.addUserLimit(userLimits);
        return ResponseEntity.ok("User Limit created successfully");
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<UserLimit> > getAllUserLimits(@PathVariable Long userId) {
        List<UserLimit> userLimitList=userLimitService.findUserLimit(userId);
        return ResponseEntity.ok(userLimitList);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserLimit(@RequestBody List<UserLimitDTO> userLimitDTOs) {
        userLimitService.updateUserLimit(userLimitDTOs);
        return ResponseEntity.ok("User Limit updated successfully");
    }

    @DeleteMapping("/delete/{userLimitId}")
    public ResponseEntity<String> deleteUserLimit(@PathVariable Long userLimitId) {
        userLimitService.deleteUserLimit(userLimitId);
        return ResponseEntity.ok("User Limit deleted successfully");
    }
}
