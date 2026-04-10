package by.bsu.fitness.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RepositoryException extends FitnessException {

    private static final Logger LOGGER = LogManager.getLogger(RepositoryException.class);

    public RepositoryException(String message) {
        super(message);
        LOGGER.error("RepositoryException: {}", message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error("RepositoryException: {}, cause: {}", message, cause.toString());
    }
}
