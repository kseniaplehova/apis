package by.bsu.fitness.service.impl;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.exception.ServiceException;
import by.bsu.fitness.factory.SubscriptionFactory;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.service.SubscriptionService;
import by.bsu.fitness.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Optional;

public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionRepository repository;
    private final Validator<Subscription> validator;
    private final SubscriptionFactory factory;

    public SubscriptionServiceImpl(SubscriptionRepository repository,
                                   Validator<Subscription> validator,
                                   SubscriptionFactory factory) {
        this.repository = repository;
        this.validator = validator;
        this.factory = factory;
        LOGGER.debug("SubscriptionServiceImpl created");
    }

    @Override
    public Subscription createSubscription(long clientId, SubscriptionType type) throws ServiceException {
        Optional<Subscription> opt = factory.createSubscription(clientId, type);
        if (opt.isEmpty()) {
            throw new ServiceException("Failed to create subscription: invalid parameters");
        }
        Subscription subscription = opt.get();
        if (!validator.validate(subscription)) {
            throw new ServiceException("Subscription validation failed");
        }
        repository.add(subscription);
        LOGGER.debug("Subscription {} created and saved", subscription.getId());
        return subscription;
    }

    @Override
    public void freeze(long id) throws ServiceException {
        Subscription s = repository.findById(id)
                .orElseThrow(() -> new ServiceException("Subscription not found: " + id));
        s.setState(SubscriptionState.FROZEN);
        LOGGER.debug("Subscription {} frozen", id);
    }

    @Override
    public void activate(long id) throws ServiceException {
        Subscription s = repository.findById(id)
                .orElseThrow(() -> new ServiceException("Subscription not found: " + id));
        s.setState(SubscriptionState.ACTIVE);
        LOGGER.debug("Subscription {} activated", id);
    }

    @Override
    public void checkExpiration(long id) throws ServiceException {
        Subscription s = repository.findById(id)
                .orElseThrow(() -> new ServiceException("Subscription not found: " + id));
        if (s.getEndDate().isBefore(LocalDate.now())) {
            s.setState(SubscriptionState.EXPIRED);
            LOGGER.debug("Subscription {} expired", id);
        }
    }
}
