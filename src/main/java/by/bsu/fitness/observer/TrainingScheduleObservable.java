package by.bsu.fitness.observer;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TrainingScheduleObservable implements Observable {

    private static final Logger LOGGER = LogManager.getLogger(TrainingScheduleObservable.class);

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        LOGGER.debug("Observer added: {}", observer.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        LOGGER.debug("Observer removed: {}", observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers(Training training) {
        LOGGER.debug("Notifying {} observers about training {}", observers.size(), training.getId());
        for (Observer observer : observers) {
            observer.update(training);
        }
    }
}
