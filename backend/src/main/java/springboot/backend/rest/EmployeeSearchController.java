package springboot.backend.rest;


import org.springframework.web.bind.annotation.*;
import springboot.backend.dto.UserSearchDTO;
import springboot.backend.models.UserAccount;
import springboot.backend.service.UserSearchService;

import java.util.List;

@RestController
@RequestMapping("/search")
public class EmployeeSearchController {

    final private UserSearchService userSearchService;
    public EmployeeSearchController(UserSearchService userSearchService){
        this.userSearchService = userSearchService;
    }

    @PostMapping("")
    public List<UserAccount> searchUser(@RequestBody UserSearchDTO request) {
        List<UserAccount> response;
        response=userSearchService.searchUser(request);
        return response;
    }
}
