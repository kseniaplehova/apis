package by.bsu.fitness.specification;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingByTypeSpecification implements TrainingSpecification {

    private static final Logger LOGGER = LogManager.getLogger(TrainingByTypeSpecification.class);

    private final TrainingType type;

    public TrainingByTypeSpecification(TrainingType type) {
        this.type = type;
        LOGGER.debug("TrainingByTypeSpecification created with type={}", type);
    }

    @Override
    public boolean match(Training training) {
        boolean result = training.getType() == type;
        LOGGER.debug("Matching training {} by type {} -> {}", training.getId(), type, result);
        return result;
    }
}
