package springboot.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import springboot.backend.models.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> ,
        JpaSpecificationExecutor<UserAccount>
{
}
