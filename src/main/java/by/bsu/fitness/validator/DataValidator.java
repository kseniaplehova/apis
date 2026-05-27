package by.bsu.fitness.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataValidator implements Validator<String> {

    private static final Logger LOGGER = LogManager.getLogger(DataValidator.class);

    @Override
    public boolean validate(String line) {
        if (line == null || line.isEmpty()) {
            LOGGER.error("Line is empty");
            return false;
        }
        String[] parts = line.split(",");
        if (parts.length < 5) {
            LOGGER.error("Invalid line format: {}", line);
            return false;
        }
        LOGGER.debug("Line validated: {}", line);
        return true;
    }
}
