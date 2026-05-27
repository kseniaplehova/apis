package by.bsu.fitness.strategy.impl;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.strategy.TrainingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonalTrainingStrategy implements TrainingStrategy {

    private static final Logger LOGGER = LogManager.getLogger(PersonalTrainingStrategy.class);

    @Override
    public void execute(Training training) {
        LOGGER.debug("Executing personal training strategy for training {}", training.getId());
        LOGGER.info("Personal training with coach {} for client {}",
                training.getCoachName(), training.getClientId());
    }
}
