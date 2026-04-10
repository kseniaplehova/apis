package by.bsu.fitness.specification;

import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class TrainingByDateSpecification implements TrainingSpecification {

    private static final Logger LOGGER = LogManager.getLogger(TrainingByDateSpecification.class);

    private final LocalDate date;

    public TrainingByDateSpecification(LocalDate date) {
        this.date = date;
        LOGGER.debug("TrainingByDateSpecification created with date={}", date);
    }

    @Override
    public boolean match(Training training) {
        boolean result = training.getDateTime().toLocalDate().equals(date);
        LOGGER.debug("Matching training {} by date {} -> {}", training.getId(), date, result);
        return result;
    }
}
