package springboot.backend.exception;

public class SearchCriteriaRequiredException extends RuntimeException {
    public SearchCriteriaRequiredException(String message) {
        super(message);
    }
}
