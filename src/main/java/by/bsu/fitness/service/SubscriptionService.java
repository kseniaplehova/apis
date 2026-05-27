package by.bsu.fitness.service;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.exception.ServiceException;

public interface SubscriptionService {
    Subscription createSubscription(long clientId, SubscriptionType type) throws ServiceException;
    void freeze(long id) throws ServiceException;
    void activate(long id) throws ServiceException;
    void checkExpiration(long id) throws ServiceException;
}
