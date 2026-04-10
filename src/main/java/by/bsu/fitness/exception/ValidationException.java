package by.bsu.fitness.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidationException extends FitnessException {

    private static final Logger LOGGER = LogManager.getLogger(ValidationException.class);

    public ValidationException(String message) {
        super(message);
        LOGGER.error("ValidationException: {}", message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error("ValidationException: {}, cause: {}", message, cause.toString());
    }
}
