package by.bsu.fitness.validator;

import by.bsu.fitness.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataValidator {

    private static final Logger LOGGER = LogManager.getLogger(DataValidator.class);

    public DataValidator() {
        LOGGER.debug("DataValidator created");
    }

    public void validateLine(String line) throws ValidationException {
        if (line == null || line.isEmpty()) {
            LOGGER.error("Line is empty");
            throw new ValidationException("Line cannot be empty");
        }

        String[] parts = line.split(",");
        if (parts.length < 5) {
            LOGGER.error("Invalid line format: {}", line);
            throw new ValidationException("Invalid data format");
        }

        LOGGER.debug("Line validated: {}", line);
    }
}
