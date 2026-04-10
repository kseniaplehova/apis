package by.bsu.fitness.service;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.exception.ServiceException;
import by.bsu.fitness.exception.ValidationException;
import by.bsu.fitness.factory.SubscriptionFactory;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.validator.SubscriptionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class SubscriptionService {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionService.class);

    private final SubscriptionRepository repository;
    private final SubscriptionValidator validator;
    private final SubscriptionFactory factory;

    public SubscriptionService(SubscriptionRepository repository,
                               SubscriptionValidator validator,
                               SubscriptionFactory factory) {
        this.repository = repository;
        this.validator = validator;
        this.factory = factory;
        LOGGER.debug("SubscriptionService created");
    }

    public Subscription createSubscription(long clientId, SubscriptionType type) throws ServiceException {
        try {
            Subscription subscription = factory.createSubscription(clientId, type);
            validator.validate(subscription);
            repository.add(subscription);
            LOGGER.debug("Subscription {} created and saved", subscription.getId());
            return subscription;

        } catch (Exception e) {
            throw new ServiceException("Subscription creation failed", e);
        }
    }

    public void freeze(long id) throws ServiceException {
        Subscription s = repository.findById(id);
        if (s == null) throw new ServiceException("Subscription not found");
        s.setState(SubscriptionState.FROZEN);
        LOGGER.debug("Subscription {} frozen", id);
    }

    public void activate(long id) throws ServiceException {
        Subscription s = repository.findById(id);
        if (s == null) throw new ServiceException("Subscription not found");
        s.setState(SubscriptionState.ACTIVE);
        LOGGER.debug("Subscription {} activated", id);
    }

    public void checkExpiration(long id) throws ServiceException {
        Subscription s = repository.findById(id);
        if (s == null) throw new ServiceException("Subscription not found");

        if (s.getEndDate().isBefore(LocalDate.now())) {
            s.setState(SubscriptionState.EXPIRED);
            LOGGER.debug("Subscription {} expired", id);
        }
    }
}
