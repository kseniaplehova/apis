package by.bsu.fitness.strategy;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GroupTrainingStrategy implements TrainingStrategy {

    private static final Logger LOGGER = LogManager.getLogger(GroupTrainingStrategy.class);

    @Override
    public void execute(Training training) {
        LOGGER.debug("Executing group training strategy for training {}", training.getId());
        LOGGER.info("Group training with coach {} for client {}",
                training.getCoachName(), training.getClientId());
    }
}
