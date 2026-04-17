package springboot.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.backend.models.Role;
import springboot.backend.models.Status;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Used to check duplicate ACTIVE role_code before creating
    Optional<Role> findByRoleCodeAndStatus(String roleCode, Status status);

    // Used for search — all three conditions are optional filters applied via service
    List<Role> findByLocationId(Long locationId);

    List<Role> findByRoleCodeContainingIgnoreCase(String roleCode);

    List<Role> findByRoleDescriptionContainingIgnoreCase(String roleDescription);
}
