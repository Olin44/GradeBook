package gradebook;

public class InvalidGradeException extends RuntimeException {
    InvalidGradeException(String message) {
        super(message);
    }
}
