package by.bsu.fitness.factory;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.exception.ValidationException;
import by.bsu.fitness.util.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class SubscriptionFactory {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionFactory.class);

    public SubscriptionFactory() {
        LOGGER.debug("SubscriptionFactory created");
    }

    public Subscription createSubscription(long clientId, SubscriptionType type) throws ValidationException {
        if (clientId <= 0) {
            LOGGER.error("Invalid clientId: {}", clientId);
            throw new ValidationException("Client ID must be positive");
        }
        if (type == null) {
            LOGGER.error("Subscription type is null");
            throw new ValidationException("Subscription type cannot be null");
        }

        long id = IdGenerator.nextId();
        LocalDate start = LocalDate.now();
        LocalDate end;

        switch (type) {
            case MONTHLY:
                end = start.plusMonths(1);
                break;
            case YEARLY:
                end = start.plusYears(1);
                break;
            case VISIT_BASED:
                end = start.plusYears(5); // условно: действует долго
                break;
            default:
                LOGGER.error("Unknown subscription type: {}", type);
                throw new ValidationException("Unknown subscription type");
        }

        Subscription subscription = new Subscription(
                id,
                clientId,
                type,
                start,
                end,
                SubscriptionState.ACTIVE
        );

        LOGGER.debug("Subscription created: {}", subscription);
        return subscription;
    }
}
