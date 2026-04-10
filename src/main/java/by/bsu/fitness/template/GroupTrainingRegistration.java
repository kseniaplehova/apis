package by.bsu.fitness.template;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.observer.Observable;
import by.bsu.fitness.strategy.TrainingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GroupTrainingRegistration extends RegistrationTemplate {

    private static final Logger LOGGER = LogManager.getLogger(GroupTrainingRegistration.class);

    public GroupTrainingRegistration(Training training,
                                     TrainingStrategy strategy,
                                     Observable observable) {
        super(training, strategy, observable);
        LOGGER.debug("GroupTrainingRegistration created for training {}", training.getId());
    }

    @Override
    protected void validate() {
        LOGGER.debug("Validating group training {}", training.getId());
    }

    @Override
    protected void reserveSlot() {
        LOGGER.debug("Reserving slot for group training {}", training.getId());
    }
}
