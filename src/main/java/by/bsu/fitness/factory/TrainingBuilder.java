package by.bsu.fitness.factory;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.util.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class TrainingBuilder {

    private static final Logger LOGGER = LogManager.getLogger(TrainingBuilder.class);

    private long clientId;
    private String coachName;
    private TrainingType type;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private TrainingState state;

    public TrainingBuilder() {
        LOGGER.debug("TrainingBuilder created");
    }

    public TrainingBuilder clientId(long clientId) {
        this.clientId = clientId;
        return this;
    }

    public TrainingBuilder coachName(String coachName) {
        this.coachName = coachName;
        return this;
    }

    public TrainingBuilder type(TrainingType type) {
        this.type = type;
        return this;
    }

    public TrainingBuilder dateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public TrainingBuilder durationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public TrainingBuilder state(TrainingState state) {
        this.state = state;
        return this;
    }

    public Training build() {
        long id = IdGenerator.nextId();
        Training training = new Training(id, clientId, coachName, type, dateTime, durationMinutes, state);
        LOGGER.debug("Training built: {}", training);
        return training;
    }
}
