package springboot.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springboot.backend.dao.FunctionRepository;
import springboot.backend.dto.FunctionRequest;
import springboot.backend.dto.FunctionResponse;
import springboot.backend.dto.FunctionSearchRequest;
import springboot.backend.exception.DuplicateFunctionException;
import springboot.backend.exception.ResourceNotFoundException;
import springboot.backend.exception.SearchCriteriaRequiredException;
import springboot.backend.models.Status;
import springboot.backend.models.SystemFunction;
import springboot.backend.service.FunctionService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepository functionRepository;

    // ─── Helper ──────────────────────────────────────────────────────────────

    private FunctionResponse toResponse(SystemFunction f) {
        return new FunctionResponse(
                f.getId(),
                f.getModule(),
                f.getFunctionId(),
                f.getFunctionDescription(),
                f.getFunctionLevel(),
                f.getStatus()
        );
    }

    // ─── Create ──────────────────────────────────────────────────────────────

    @Override
    public FunctionResponse createFunction(FunctionRequest request) {

        // Validate mandatory fields
        if (request.getModule() == null) {
            throw new IllegalArgumentException("module is required");
        }
        if (!StringUtils.hasText(request.getFunctionId())) {
            throw new IllegalArgumentException("functionId is required");
        }
        if (!StringUtils.hasText(request.getFunctionDescription())) {
            throw new IllegalArgumentException("functionDescription is required");
        }
        if (request.getFunctionLevel() == null) {
            throw new IllegalArgumentException("functionLevel is required");
        }
        if (request.getFunctionLevel() < 1) {
            throw new IllegalArgumentException("functionLevel must be >= 1");
        }
        if (request.getStatus() == null) {
            throw new IllegalArgumentException("status is required");
        }

        // Normalise inputs
        String normalizedFunctionId = request.getFunctionId().trim().toUpperCase();
        String trimmedDescription   = request.getFunctionDescription().trim();

        // Duplicate check — function_id must be globally unique
        if (functionRepository.existsByFunctionIdIgnoreCase(normalizedFunctionId)) {
            throw new DuplicateFunctionException(
                    "A function with functionId '" + normalizedFunctionId + "' already exists.");
        }

        SystemFunction function = new SystemFunction();
        function.setModule(request.getModule());
        function.setFunctionId(normalizedFunctionId);
        function.setFunctionDescription(trimmedDescription);
        function.setFunctionLevel(request.getFunctionLevel());
        function.setStatus(request.getStatus());

        return toResponse(functionRepository.save(function));
    }

    // ─── Update ──────────────────────────────────────────────────────────────

    @Override
    public FunctionResponse updateFunction(Long id, FunctionRequest request) {

        SystemFunction function = functionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Function not found with id: " + id));

        // functionId is NOT updatable — intentionally ignored even if provided

        if (request.getModule() != null) {
            function.setModule(request.getModule());
        }
        if (StringUtils.hasText(request.getFunctionDescription())) {
            function.setFunctionDescription(request.getFunctionDescription().trim());
        }
        if (request.getFunctionLevel() != null) {
            if (request.getFunctionLevel() < 1) {
                throw new IllegalArgumentException("functionLevel must be >= 1");
            }
            function.setFunctionLevel(request.getFunctionLevel());
        }
        if (request.getStatus() != null) {
            function.setStatus(request.getStatus());
        }

        return toResponse(functionRepository.save(function));
    }

    // ─── Soft Delete ─────────────────────────────────────────────────────────

    @Override
    public void deleteFunction(Long id) {

        SystemFunction function = functionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Function not found with id: " + id));

        function.setStatus(Status.INACTIVE);
        functionRepository.save(function);
    }

    // ─── Get All ─────────────────────────────────────────────────────────────

    @Override
    public List<FunctionResponse> getAllFunctions() {
        return functionRepository.findAllByOrderByFunctionIdAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── Get By ID ───────────────────────────────────────────────────────────

    @Override
    public FunctionResponse getFunctionById(Long id) {
        SystemFunction function = functionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Function not found with id: " + id));
        return toResponse(function);
    }

    // ─── Search ──────────────────────────────────────────────────────────────

    @Override
    public List<FunctionResponse> searchFunctions(FunctionSearchRequest searchRequest) {

        boolean hasModule      = searchRequest.getModule() != null;
        boolean hasFunctionId  = StringUtils.hasText(searchRequest.getFunctionId());
        boolean hasDescription = StringUtils.hasText(searchRequest.getFunctionDescription());
        boolean hasLevel       = searchRequest.getFunctionLevel() != null;
        boolean hasStatus      = searchRequest.getStatus() != null;

        // At least one search criterion is required
        if (!hasModule && !hasFunctionId && !hasDescription && !hasLevel && !hasStatus) {
            throw new SearchCriteriaRequiredException(
                    "At least one search field (module, functionId, functionDescription, functionLevel, status) is required.");
        }

        // Collect matching sets per provided criterion and intersect them (AND logic)
        List<Set<SystemFunction>> matchingSets = new ArrayList<>();

        if (hasModule) {
            matchingSets.add(new LinkedHashSet<>(
                    functionRepository.findByModule(searchRequest.getModule())));
        }
        if (hasFunctionId) {
            matchingSets.add(new LinkedHashSet<>(
                    functionRepository.findByFunctionIdContainingIgnoreCase(searchRequest.getFunctionId())));
        }
        if (hasDescription) {
            matchingSets.add(new LinkedHashSet<>(
                    functionRepository.findByFunctionDescriptionContainingIgnoreCase(searchRequest.getFunctionDescription())));
        }
        if (hasLevel) {
            matchingSets.add(new LinkedHashSet<>(
                    functionRepository.findByFunctionLevel(searchRequest.getFunctionLevel())));
        }
        if (hasStatus) {
            matchingSets.add(new LinkedHashSet<>(
                    functionRepository.findByStatus(searchRequest.getStatus())));
        }

        // Intersect all matching sets
        Set<SystemFunction> result = matchingSets.get(0);
        for (int i = 1; i < matchingSets.size(); i++) {
            result.retainAll(matchingSets.get(i));
        }

        return result.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
