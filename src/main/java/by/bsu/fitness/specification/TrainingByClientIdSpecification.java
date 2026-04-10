package by.bsu.fitness.specification;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingByClientIdSpecification implements TrainingSpecification {

    private static final Logger LOGGER = LogManager.getLogger(TrainingByClientIdSpecification.class);

    private final long clientId;

    public TrainingByClientIdSpecification(long clientId) {
        this.clientId = clientId;
        LOGGER.debug("TrainingByClientIdSpecification created with clientId={}", clientId);
    }

    @Override
    public boolean match(Training training) {
        boolean result = training.getClientId() == clientId;
        LOGGER.debug("Matching training {} by clientId {} -> {}", training.getId(), clientId, result);
        return result;
    }
}
