package by.bsu.fitness.template;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.observer.Observable;
import by.bsu.fitness.strategy.TrainingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RegistrationTemplate {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationTemplate.class);

    protected final Training training;
    protected final TrainingStrategy strategy;
    protected final Observable observable;

    protected RegistrationTemplate(Training training,
                                   TrainingStrategy strategy,
                                   Observable observable) {
        this.training = training;
        this.strategy = strategy;
        this.observable = observable;
        LOGGER.debug("RegistrationTemplate created for training {}", training.getId());
    }

    public final void register() {
        LOGGER.debug("Starting registration process for training {}", training.getId());
        validate();
        reserveSlot();
        applyStrategy();
        notifyClients();
        LOGGER.debug("Registration process finished for training {}", training.getId());
    }

    protected abstract void validate();

    protected abstract void reserveSlot();

    protected void applyStrategy() {
        LOGGER.debug("Applying strategy for training {}", training.getId());
        strategy.execute(training);
    }

    protected void notifyClients() {
        LOGGER.debug("Notifying observers about training {}", training.getId());
        observable.notifyObservers(training);
    }
}
