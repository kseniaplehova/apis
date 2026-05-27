package by.bsu.fitness.factory;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.util.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

public class SubscriptionFactory {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionFactory.class);

    // Method 1: returns Optional — no exception thrown, caller decides how to handle absence
    public Optional<Subscription> createSubscription(long clientId, SubscriptionType type) {
        if (clientId <= 0) {
            LOGGER.error("Invalid clientId: {}", clientId);
            return Optional.empty();
        }
        if (type == null) {
            LOGGER.error("Subscription type is null");
            return Optional.empty();
        }
        Subscription subscription = build(clientId, type);
        LOGGER.debug("Subscription created: {}", subscription);
        return Optional.of(subscription);
    }

    // Method 2: type is provided lazily via Supplier — useful when the type is computed at call time
    public Optional<Subscription> createFromSupplier(long clientId, Supplier<SubscriptionType> typeSupplier) {
        if (typeSupplier == null) {
            LOGGER.error("Type supplier is null");
            return Optional.empty();
        }
        return createSubscription(clientId, typeSupplier.get());
    }

    // Method 3: returns a Supplier that defers creation — caller decides when to trigger it
    public Supplier<Optional<Subscription>> toSupplier(long clientId, SubscriptionType type) {
        return () -> createSubscription(clientId, type);
    }

    // Method 4: combination — type from Supplier, result wrapped in Supplier for lazy evaluation
    public Supplier<Optional<Subscription>> toSupplier(long clientId, Supplier<SubscriptionType> typeSupplier) {
        return () -> createFromSupplier(clientId, typeSupplier);
    }

    private Subscription build(long clientId, SubscriptionType type) {
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
            default: // VISIT_BASED
                end = start.plusYears(5);
                break;
        }
        return new Subscription(id, clientId, type, start, end, SubscriptionState.ACTIVE);
    }
}
