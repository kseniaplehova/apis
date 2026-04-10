package by.bsu.fitness.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FitnessException extends Exception {

    private static final Logger LOGGER = LogManager.getLogger(FitnessException.class);

    public FitnessException(String message) {
        super(message);
        LOGGER.error("FitnessException created: {}", message);
    }

    public FitnessException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error("FitnessException created: {}, cause: {}", message, cause.toString());
    }
}
