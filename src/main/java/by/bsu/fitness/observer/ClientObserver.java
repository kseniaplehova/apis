package by.bsu.fitness.observer;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.entity.Training;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientObserver implements Observer {

    private static final Logger LOGGER = LogManager.getLogger(ClientObserver.class);

    private final Client client;

    public ClientObserver(Client client) {
        this.client = client;
        LOGGER.debug("ClientObserver created for client {} ({})", client.getId(), client.getName());
    }

    @Override
    public void update(Training training) {
        LOGGER.info("Client {} ({}) notified about new training: {}",
                client.getId(), client.getName(), training);
    }

    public Client getClient() {
        return client;
    }
}
