package by.bsu.fitness.validator;

import by.bsu.fitness.entity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SubscriptionValidator implements Validator<Subscription> {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionValidator.class);

    @Override
    public boolean validate(Subscription subscription) {
        if (subscription == null) {
            LOGGER.error("Subscription is null");
            return false;
        }
        if (subscription.getClientId() <= 0) {
            LOGGER.error("Invalid clientId: {}", subscription.getClientId());
            return false;
        }
        if (subscription.getStartDate() == null || subscription.getEndDate() == null) {
            LOGGER.error("Subscription dates are null");
            return false;
        }
        if (subscription.getEndDate().isBefore(subscription.getStartDate())) {
            LOGGER.error("End date {} is before start date {}", subscription.getEndDate(), subscription.getStartDate());
            return false;
        }
        LOGGER.debug("Subscription {} validated successfully", subscription.getId());
        return true;
    }
}
