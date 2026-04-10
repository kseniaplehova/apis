package by.bsu.fitness.service;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.observer.ClientObserver;
import by.bsu.fitness.observer.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificationService {

    private static final Logger LOGGER = LogManager.getLogger(NotificationService.class);

    private final Observable observable;

    public NotificationService(Observable observable) {
        this.observable = observable;
        LOGGER.debug("NotificationService created");
    }

    public void subscribeClient(Client client) {
        ClientObserver observer = new ClientObserver(client);
        observable.addObserver(observer);
        LOGGER.debug("Client {} ({}) subscribed to notifications", client.getId(), client.getName());
    }

    public void unsubscribeClient(ClientObserver observer) {
        observable.removeObserver(observer);
        LOGGER.debug("Client {} ({}) unsubscribed from notifications",
                observer.getClient().getId(), observer.getClient().getName());
    }
}
