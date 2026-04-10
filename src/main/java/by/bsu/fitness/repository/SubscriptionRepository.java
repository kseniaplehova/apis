package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionRepository {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionRepository.class);

    private final Map<Long, Subscription> subscriptions = new HashMap<>();

    public void add(Subscription subscription) {
        subscriptions.put(subscription.getId(), subscription);
        LOGGER.debug("Subscription added: {}", subscription);
    }

    public Subscription findById(long id) {
        Subscription s = subscriptions.get(id);
        LOGGER.debug("Subscription lookup by id {} -> {}", id, s);
        return s;
    }

    // ✔ Вот этот метод нужен TrainingService
    public Subscription findByClientId(long clientId) {
        for (Subscription s : subscriptions.values()) {
            if (s.getClientId() == clientId) {
                LOGGER.debug("Subscription lookup by clientId {} -> {}", clientId, s);
                return s;
            }
        }
        LOGGER.debug("Subscription lookup by clientId {} -> null", clientId);
        return null;
    }
}
