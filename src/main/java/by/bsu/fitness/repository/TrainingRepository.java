package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.exception.RepositoryException;
import by.bsu.fitness.specification.TrainingSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrainingRepository {

    private static final Logger LOGGER = LogManager.getLogger(TrainingRepository.class);

    private final List<Training> trainings = new ArrayList<>();

    public void add(Training training) throws RepositoryException {
        if (training == null) {
            LOGGER.error("Attempt to add null training");
            throw new RepositoryException("Training cannot be null");
        }
        trainings.add(training);
        LOGGER.debug("Training added: {}", training);
    }

    public List<Training> findAll() {
        LOGGER.debug("Returning all trainings, count={}", trainings.size());
        return new ArrayList<>(trainings);
    }

    public List<Training> query(TrainingSpecification specification) throws RepositoryException {
        if (specification == null) {
            LOGGER.error("Specification is null");
            throw new RepositoryException("Specification cannot be null");
        }

        List<Training> result = new ArrayList<>();
        for (Training training : trainings) {
            if (specification.match(training)) {
                result.add(training);
            }
        }

        LOGGER.debug("Query result size: {}", result.size());
        return result;
    }

    public List<Training> sort(Comparator<Training> comparator) throws RepositoryException {
        if (comparator == null) {
            LOGGER.error("Comparator is null");
            throw new RepositoryException("Comparator cannot be null");
        }

        List<Training> sorted = new ArrayList<>(trainings);
        sorted.sort(comparator);

        LOGGER.debug("Trainings sorted, count={}", sorted.size());
        return sorted;
    }
}
