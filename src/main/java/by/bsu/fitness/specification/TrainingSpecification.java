package by.bsu.fitness.specification;

import by.bsu.fitness.entity.Training;

public interface TrainingSpecification {
    boolean match(Training training);
}
