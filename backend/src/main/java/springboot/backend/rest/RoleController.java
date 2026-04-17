package springboot.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.backend.dto.RoleRequest;
import springboot.backend.dto.RoleResponse;
import springboot.backend.dto.RoleSearchRequest;
import springboot.backend.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // POST /api/roles — Create a new role
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request) {
        RoleResponse response = roleService.createRole(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/roles — Get all roles
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // GET /api/roles/{id} — Get a single role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // PUT /api/roles/{id} — Update role (roleCode is immutable)
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    // DELETE /api/roles/{id} — Soft delete (sets status to INACTIVE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/roles/search — Search roles by criteria (at least one required)
    @PostMapping("/search")
    public ResponseEntity<List<RoleResponse>> searchRoles(@RequestBody RoleSearchRequest searchRequest) {
        return ResponseEntity.ok(roleService.searchRoles(searchRequest));
    }
}
