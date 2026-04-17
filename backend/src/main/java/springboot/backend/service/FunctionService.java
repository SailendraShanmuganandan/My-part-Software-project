package springboot.backend.service;

import springboot.backend.dto.FunctionRequest;
import springboot.backend.dto.FunctionResponse;
import springboot.backend.dto.FunctionSearchRequest;

import java.util.List;

public interface FunctionService {

    FunctionResponse createFunction(FunctionRequest request);

    FunctionResponse updateFunction(Long id, FunctionRequest request);

    void deleteFunction(Long id);

    List<FunctionResponse> getAllFunctions();

    FunctionResponse getFunctionById(Long id);

    List<FunctionResponse> searchFunctions(FunctionSearchRequest searchRequest);
}
