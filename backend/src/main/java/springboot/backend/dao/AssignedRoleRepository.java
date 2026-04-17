package springboot.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.backend.models.AssignedRole;

import java.util.List;

public interface AssignedRoleRepository extends JpaRepository<AssignedRole, Long> {
    List<AssignedRole> findByRoleCode(String roleCode);
}


