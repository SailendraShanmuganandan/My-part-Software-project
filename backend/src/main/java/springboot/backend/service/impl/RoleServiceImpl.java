package springboot.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springboot.backend.dao.LocationRepository;
import springboot.backend.dao.RoleRepository;
import springboot.backend.dto.RoleRequest;
import springboot.backend.dto.RoleResponse;
import springboot.backend.dto.RoleSearchRequest;
import springboot.backend.exception.DuplicateRoleException;
import springboot.backend.exception.ResourceNotFoundException;
import springboot.backend.exception.SearchCriteriaRequiredException;
import springboot.backend.models.Role;
import springboot.backend.models.Status;
import springboot.backend.service.RoleService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private RoleResponse toResponse(Role role) {
        String locationName;
        if (role.getLocationId() == 0) {
            locationName = "ALL LOCATION";
        } else {
            locationName = locationRepository.findById(role.getLocationId())
                    .map(loc -> loc.getLocationName())
                    .orElse("Unknown Location");
        }
        return new RoleResponse(
                role.getId(),
                locationName,
                role.getRoleCode(),
                role.getRoleDescription(),
                role.getStatus());
    }

    // ─── Create ──────────────────────────────────────────────────────────────

    @Override
    public RoleResponse createRole(RoleRequest request) {

        // Validation: all fields are mandatory
        if (request.getLocationId() == null) {
            throw new IllegalArgumentException("locationId is required");
        }
        if (!StringUtils.hasText(request.getRoleCode())) {
            throw new IllegalArgumentException("roleCode is required");
        }
        if (!StringUtils.hasText(request.getRoleDescription())) {
            throw new IllegalArgumentException("roleDescription is required");
        }
        if (request.getStatus() == null) {
            throw new IllegalArgumentException("status is required");
        }

        // Duplicate check: no two ACTIVE roles with the same role_code
        roleRepository.findByRoleCodeAndStatus(request.getRoleCode(), Status.ACTIVE)
                .ifPresent(existing -> {
                    throw new DuplicateRoleException(
                            "An ACTIVE role with code '" + request.getRoleCode() + "' already exists.");
                });

        Role role = new Role();
        role.setLocationId(request.getLocationId());
        role.setRoleCode(request.getRoleCode());
        role.setRoleDescription(request.getRoleDescription());
        role.setStatus(request.getStatus());

        return toResponse(roleRepository.save(role));
    }

    // ─── Update ──────────────────────────────────────────────────────────────

    @Override
    public RoleResponse updateRole(Long id, RoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        // Only locationId, roleDescription and status are updatable
        if (request.getLocationId() != null) {
            role.setLocationId(request.getLocationId());
        }
        if (StringUtils.hasText(request.getRoleDescription())) {
            role.setRoleDescription(request.getRoleDescription());
        }
        if (request.getStatus() != null) {
            role.setStatus(request.getStatus());
        }

        // roleCode is NOT updated — intentionally ignored even if provided

        return toResponse(roleRepository.save(role));
    }

    // ─── Soft Delete ─────────────────────────────────────────────────────────

    @Override
    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        role.setStatus(Status.INACTIVE);
        roleRepository.save(role);
    }

    // ─── Get All ─────────────────────────────────────────────────────────────

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── Get By ID ───────────────────────────────────────────────────────────

    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return toResponse(role);
    }

    // ─── Search ──────────────────────────────────────────────────────────────

    @Override
    public List<RoleResponse> searchRoles(RoleSearchRequest searchRequest) {

        boolean hasLocationId = searchRequest.getLocationId() != null;
        boolean hasRoleCode = StringUtils.hasText(searchRequest.getRoleCode());
        boolean hasRoleDesc = StringUtils.hasText(searchRequest.getRoleDescription());

        // At least one search criterion is required
        if (!hasLocationId && !hasRoleCode && !hasRoleDesc) {
            throw new SearchCriteriaRequiredException(
                    "At least one search field (locationId, roleCode, roleDescription) is required.");
        }

        // Collect matching sets per provided criterion and intersect them
        List<Set<Role>> matchingSets = new ArrayList<>();

        if (hasLocationId) {
            matchingSets.add(new LinkedHashSet<>(
                    roleRepository.findByLocationId(searchRequest.getLocationId())));
        }
        if (hasRoleCode) {
            matchingSets.add(new LinkedHashSet<>(
                    roleRepository.findByRoleCodeContainingIgnoreCase(searchRequest.getRoleCode())));
        }
        if (hasRoleDesc) {
            matchingSets.add(new LinkedHashSet<>(
                    roleRepository.findByRoleDescriptionContainingIgnoreCase(searchRequest.getRoleDescription())));
        }

        // Intersect all sets (AND logic across criteria)
        Set<Role> result = matchingSets.get(0);
        for (int i = 1; i < matchingSets.size(); i++) {
            result.retainAll(matchingSets.get(i));
        }

        return result.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
