package by.bsu.fitness.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceException extends FitnessException {

    private static final Logger LOGGER = LogManager.getLogger(ServiceException.class);

    public ServiceException(String message) {
        super(message);
        LOGGER.error("ServiceException: {}", message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error("ServiceException: {}, cause: {}", message, cause.toString());
    }
}
