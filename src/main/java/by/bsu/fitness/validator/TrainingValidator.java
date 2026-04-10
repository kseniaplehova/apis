package by.bsu.fitness.validator;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingValidator {

    private static final Logger LOGGER = LogManager.getLogger(TrainingValidator.class);

    public TrainingValidator() {
        LOGGER.debug("TrainingValidator created");
    }

    public void validate(Training training) throws ValidationException {
        if (training == null) {
            LOGGER.error("Training is null");
            throw new ValidationException("Training cannot be null");
        }
        if (training.getClientId() <= 0) {
            LOGGER.error("Invalid clientId: {}", training.getClientId());
            throw new ValidationException("Client ID must be positive");
        }
        if (training.getDurationMinutes() <= 0) {
            LOGGER.error("Invalid duration: {}", training.getDurationMinutes());
            throw new ValidationException("Duration must be positive");
        }
        if (training.getCoachName() == null || training.getCoachName().isEmpty()) {
            LOGGER.error("Coach name is empty");
            throw new ValidationException("Coach name cannot be empty");
        }

        LOGGER.debug("Training {} validated successfully", training.getId());
    }
}
