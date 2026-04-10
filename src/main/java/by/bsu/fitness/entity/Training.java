package by.bsu.fitness.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Training {

    private static final Logger LOGGER = LogManager.getLogger(Training.class);

    private final long id;
    private final long clientId;
    private final String coachName;
    private final TrainingType type;
    private final LocalDateTime dateTime;
    private final int durationMinutes;
    private TrainingState state;

    public Training(long id,
                    long clientId,
                    String coachName,
                    TrainingType type,
                    LocalDateTime dateTime,
                    int durationMinutes,
                    TrainingState state) {
        this.id = id;
        this.clientId = clientId;
        this.coachName = coachName;
        this.type = type;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.state = state;
        LOGGER.debug("Training created: {}", this);
    }

    public long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public String getCoachName() {
        return coachName;
    }

    public TrainingType getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public TrainingState getState() {
        return state;
    }

    public void setState(TrainingState state) {
        LOGGER.debug("Changing training state from {} to {}", this.state, state);
        this.state = state;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", coachName='" + coachName + '\'' +
                ", type=" + type +
                ", dateTime=" + dateTime +
                ", durationMinutes=" + durationMinutes +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Training training = (Training) o;

        if (id != training.id) {
            return false;
        }
        if (clientId != training.clientId) {
            return false;
        }
        if (durationMinutes != training.durationMinutes) {
            return false;
        }
        if (coachName != null ? !coachName.equals(training.coachName) : training.coachName != null) {
            return false;
        }
        if (type != training.type) {
            return false;
        }
        if (dateTime != null ? !dateTime.equals(training.dateTime) : training.dateTime != null) {
            return false;
        }
        return state == training.state;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + (coachName != null ? coachName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + durationMinutes;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
