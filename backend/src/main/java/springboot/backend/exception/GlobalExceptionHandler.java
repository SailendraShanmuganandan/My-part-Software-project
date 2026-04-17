package springboot.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── 404 Not Found ────────────────────────────────────────────────────────
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage());
    }

    // ── 409 Conflict — Duplicate Active Role ─────────────────────────────────
    @ExceptionHandler(DuplicateRoleException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateRole(DuplicateRoleException ex) {
        return buildResponse(HttpStatus.CONFLICT, "DUPLICATE_ROLE", ex.getMessage());
    }

    // ── 409 Conflict — Duplicate Function ID ─────────────────────────────────
    @ExceptionHandler(DuplicateFunctionException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateFunction(DuplicateFunctionException ex) {
        return buildResponse(HttpStatus.CONFLICT, "DUPLICATE_FUNCTION", ex.getMessage());
    }

    // ── 400 Bad Request — Missing Search Criteria ────────────────────────────
    @ExceptionHandler(SearchCriteriaRequiredException.class)
    public ResponseEntity<Map<String, Object>> handleSearchCriteriaRequired(SearchCriteriaRequiredException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "SEARCH_CRITERIA_REQUIRED", ex.getMessage());
    }

    // ── 400 Bad Request — Validation / Illegal Argument ─────────────────────
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", ex.getMessage());
    }

    // ── 500 Internal Server Error — Catch-All ────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "An unexpected error occurred: " + ex.getMessage());
    }

    // ── Helper ───────────────────────────────────────────────────────────────
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
