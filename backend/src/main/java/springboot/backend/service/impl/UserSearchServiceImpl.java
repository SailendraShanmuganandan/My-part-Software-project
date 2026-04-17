package springboot.backend.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springboot.backend.dao.UserAccountRepository;
import springboot.backend.dto.UserSearchDTO;
import springboot.backend.models.UserAccount;
import springboot.backend.service.UserSearchService;
import springboot.backend.specification.UserSearchSpecification;

import java.util.List;
@Service
public class UserSearchServiceImpl implements UserSearchService {

    final private UserAccountRepository userAccountRepository;

    public UserSearchServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }


    @Override
    public List<UserAccount> searchUser(UserSearchDTO request) {
        Specification<UserAccount> spec = UserSearchSpecification.searchUserAccount(request);
        return userAccountRepository.findAll(spec);
    }
}
