package by.bsu.fitness.strategy;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface TrainingStrategy {

    Logger LOGGER = LogManager.getLogger(TrainingStrategy.class);

    void execute(Training training);
}
