package by.bsu.fitness.specification;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingByCoachNameSpecification implements TrainingSpecification {

    private static final Logger LOGGER = LogManager.getLogger(TrainingByCoachNameSpecification.class);

    private final String coachName;

    public TrainingByCoachNameSpecification(String coachName) {
        this.coachName = coachName;
        LOGGER.debug("TrainingByCoachNameSpecification created with coachName={}", coachName);
    }

    @Override
    public boolean match(Training training) {
        boolean result = training.getCoachName().equals(coachName);
        LOGGER.debug("Matching training {} by coachName {} -> {}", training.getId(), coachName, result);
        return result;
    }
}
