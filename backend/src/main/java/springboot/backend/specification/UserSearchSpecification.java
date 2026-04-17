package springboot.backend.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import springboot.backend.dto.UserSearchDTO;
import springboot.backend.models.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class UserSearchSpecification {
    public static Specification<UserAccount> searchUserAccount(UserSearchDTO req) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            query.distinct(true);

            if(req.getUserId()!=null && !req.getUserId().trim().isEmpty()){
                try {
                    Long userId = Long.parseLong(req.getUserId());
                    predicates.add(criteriaBuilder.equal(root.get("userId"), req.getUserId().trim()));
                }
               catch (NumberFormatException e) {

               }
            }

            if(req.getEpfNumber()!=null && !req.getEpfNumber().trim().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("epfNo"), req.getEpfNumber().trim()));
            }
            if(req.getIdNumber()!=null && !req.getIdNumber().trim().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("idNumber"), req.getIdNumber().trim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            //should handle error when the pridecicate empty get the error
        };

    }
}
