package by.bsu.fitness.service.impl;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.observer.ClientObserver;
import by.bsu.fitness.observer.Observable;
import by.bsu.fitness.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LogManager.getLogger(NotificationServiceImpl.class);

    private final Observable observable;

    public NotificationServiceImpl(Observable observable) {
        this.observable = observable;
        LOGGER.debug("NotificationServiceImpl created");
    }

    @Override
    public void subscribeClient(Client client) {
        ClientObserver observer = new ClientObserver(client);
        observable.addObserver(observer);
        LOGGER.debug("Client {} ({}) subscribed to notifications", client.getId(), client.getName());
    }

    @Override
    public void unsubscribeClient(ClientObserver observer) {
        observable.removeObserver(observer);
        LOGGER.debug("Client {} ({}) unsubscribed from notifications",
                observer.getClient().getId(), observer.getClient().getName());
    }
}
