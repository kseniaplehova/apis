package by.bsu.fitness.validator;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SubscriptionValidator {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionValidator.class);

    public SubscriptionValidator() {
        LOGGER.debug("SubscriptionValidator created");
    }

    public void validate(Subscription subscription) throws ValidationException {
        if (subscription == null) {
            LOGGER.error("Subscription is null");
            throw new ValidationException("Subscription cannot be null");
        }
        if (subscription.getClientId() <= 0) {
            LOGGER.error("Invalid clientId: {}", subscription.getClientId());
            throw new ValidationException("Client ID must be positive");
        }
        if (subscription.getStartDate() == null || subscription.getEndDate() == null) {
            LOGGER.error("Subscription dates are null");
            throw new ValidationException("Subscription dates cannot be null");
        }
        if (subscription.getEndDate().isBefore(subscription.getStartDate())) {
            LOGGER.error("End date {} is before start date {}", subscription.getEndDate(), subscription.getStartDate());
            throw new ValidationException("End date cannot be before start date");
        }

        LOGGER.debug("Subscription {} validated successfully", subscription.getId());
    }
}
