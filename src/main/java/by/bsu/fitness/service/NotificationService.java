package by.bsu.fitness.service;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.observer.ClientObserver;

public interface NotificationService {
    void subscribeClient(Client client);
    void unsubscribeClient(ClientObserver observer);
}
