package by.bsu.fitness.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class Subscription {

    private static final Logger LOGGER = LogManager.getLogger(Subscription.class);

    private final long id;
    private final long clientId;
    private final SubscriptionType type;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private SubscriptionState state;

    public Subscription(long id,
                        long clientId,
                        SubscriptionType type,
                        LocalDate startDate,
                        LocalDate endDate,
                        SubscriptionState state) {
        this.id = id;
        this.clientId = clientId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        LOGGER.debug("Subscription created: {}", this);
    }

    public long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public SubscriptionType getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SubscriptionState getState() {
        return state;
    }

    public void setState(SubscriptionState state) {
        LOGGER.debug("Changing subscription state from {} to {}", this.state, state);
        this.state = state;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", type=" + type +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
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

        Subscription that = (Subscription) o;

        if (id != that.id) {
            return false;
        }
        if (clientId != that.clientId) {
            return false;
        }
        if (type != that.type) {
            return false;
        }
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
            return false;
        }
        return state == that.state;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
