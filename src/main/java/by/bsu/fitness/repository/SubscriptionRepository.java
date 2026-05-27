package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionRepository {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionRepository.class);

    private final List<Subscription> subscriptions = new ArrayList<>();

    public void add(Subscription subscription) {
        subscriptions.add(subscription);
        LOGGER.debug("Subscription added: {}", subscription);
    }

    public Optional<Subscription> findById(long id) {
        Optional<Subscription> result = subscriptions.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        LOGGER.debug("Subscription lookup by id {} -> {}", id, result.isPresent() ? result.get() : "null");
        return result;
    }

    public Optional<Subscription> findByClientId(long clientId) {
        Optional<Subscription> result = subscriptions.stream()
                .filter(s -> s.getClientId() == clientId)
                .findFirst();
        LOGGER.debug("Subscription lookup by clientId {} -> {}", clientId, result.isPresent() ? result.get() : "null");
        return result;
    }
}
