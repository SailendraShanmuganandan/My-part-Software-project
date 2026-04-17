package springboot.backend.service;

import springboot.backend.dto.RoleRequest;
import springboot.backend.dto.RoleResponse;
import springboot.backend.dto.RoleSearchRequest;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    RoleResponse updateRole(Long id, RoleRequest request);

    void deleteRole(Long id);

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById(Long id);

    List<RoleResponse> searchRoles(RoleSearchRequest searchRequest);
}
