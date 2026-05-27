package by.bsu.fitness.validator;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingValidator implements Validator<Training> {

    private static final Logger LOGGER = LogManager.getLogger(TrainingValidator.class);

    @Override
    public boolean validate(Training training) {
        if (training == null) {
            LOGGER.error("Training is null");
            return false;
        }
        if (training.getClientId() <= 0) {
            LOGGER.error("Invalid clientId: {}", training.getClientId());
            return false;
        }
        if (training.getDurationMinutes() <= 0) {
            LOGGER.error("Invalid duration: {}", training.getDurationMinutes());
            return false;
        }
        if (training.getCoachName() == null || training.getCoachName().isEmpty()) {
            LOGGER.error("Coach name is empty");
            return false;
        }
        LOGGER.debug("Training {} validated successfully", training.getId());
        return true;
    }
}
