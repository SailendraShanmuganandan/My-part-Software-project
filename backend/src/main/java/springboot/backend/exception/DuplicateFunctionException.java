package springboot.backend.exception;

public class DuplicateFunctionException extends RuntimeException {
    public DuplicateFunctionException(String message) {
        super(message);
    }
}
