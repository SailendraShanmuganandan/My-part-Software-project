package springboot.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.backend.models.UserLimit;

import java.util.List;

@Repository
public interface UserLimitRepository extends JpaRepository<UserLimit, Long> {
    List<UserLimit> findAllByUserId(Long userId);

}
