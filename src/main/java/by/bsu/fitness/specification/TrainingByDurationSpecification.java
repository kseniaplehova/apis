package by.bsu.fitness.specification;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingByDurationSpecification implements TrainingSpecification {

    private static final Logger LOGGER = LogManager.getLogger(TrainingByDurationSpecification.class);

    private final int duration;

    public TrainingByDurationSpecification(int duration) {
        this.duration = duration;
        LOGGER.debug("TrainingByDurationSpecification created with duration={}", duration);
    }

    @Override
    public boolean match(Training training) {
        boolean result = training.getDurationMinutes() == duration;
        LOGGER.debug("Matching training {} by duration {} -> {}", training.getId(), duration, result);
        return result;
    }
}
