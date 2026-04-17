package springboot.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.backend.dto.FunctionRequest;
import springboot.backend.dto.FunctionResponse;
import springboot.backend.dto.FunctionSearchRequest;
import springboot.backend.service.FunctionService;

import java.util.List;

@RestController
@RequestMapping("/api/functions")
@RequiredArgsConstructor
public class FunctionController {

    private final FunctionService functionService;

    // POST /api/functions — Create a new function
    @PostMapping
    public ResponseEntity<FunctionResponse> createFunction(@RequestBody FunctionRequest request) {
        FunctionResponse response = functionService.createFunction(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/functions — Get all functions (sorted by functionId ASC)
    @GetMapping
    public ResponseEntity<List<FunctionResponse>> getAllFunctions() {
        return ResponseEntity.ok(functionService.getAllFunctions());
    }

    // GET /api/functions/{id} — Get a single function by ID
    @GetMapping("/{id}")
    public ResponseEntity<FunctionResponse> getFunctionById(@PathVariable Long id) {
        return ResponseEntity.ok(functionService.getFunctionById(id));
    }

    // PUT /api/functions/{id} — Update function (functionId is immutable)
    @PutMapping("/{id}")
    public ResponseEntity<FunctionResponse> updateFunction(
            @PathVariable Long id,
            @RequestBody FunctionRequest request) {
        return ResponseEntity.ok(functionService.updateFunction(id, request));
    }

    // DELETE /api/functions/{id} — Soft delete (sets status to INACTIVE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFunction(@PathVariable Long id) {
        functionService.deleteFunction(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/functions/search — Search functions by criteria (at least one required)
    @PostMapping("/search")
    public ResponseEntity<List<FunctionResponse>> searchFunctions(@RequestBody FunctionSearchRequest searchRequest) {
        return ResponseEntity.ok(functionService.searchFunctions(searchRequest));
    }
}
