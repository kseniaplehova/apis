package by.bsu.fitness.service;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.exception.ServiceException;

public interface TrainingService {
    void registerTraining(Training training) throws ServiceException;
}
