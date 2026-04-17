package springboot.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.backend.models.ModuleType;
import springboot.backend.models.Status;
import springboot.backend.models.SystemFunction;

import java.util.List;

public interface FunctionRepository extends JpaRepository<SystemFunction, Long> {

    // Duplicate check — case-insensitive; function_id must be globally unique
    boolean existsByFunctionIdIgnoreCase(String functionId);

    List<SystemFunction> findByModule(ModuleType module);

    List<SystemFunction> findByFunctionIdContainingIgnoreCase(String functionId);

    List<SystemFunction> findByFunctionDescriptionContainingIgnoreCase(String functionDescription);

    List<SystemFunction> findByFunctionLevel(Integer functionLevel);

    List<SystemFunction> findByStatus(Status status);

    // Get all sorted by functionId ascending
    List<SystemFunction> findAllByOrderByFunctionIdAsc();
}
