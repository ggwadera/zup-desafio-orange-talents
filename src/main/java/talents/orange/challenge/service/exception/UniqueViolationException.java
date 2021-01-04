package talents.orange.challenge.service.exception;

public class UniqueViolationException extends Exception {

    public UniqueViolationException() {
        super();
    }

    public UniqueViolationException(String message) {
        super(message);
    }

    public UniqueViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
