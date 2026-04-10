package by.bsu.fitness.observer;

import by.bsu.fitness.entity.Training;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Training training);
}
