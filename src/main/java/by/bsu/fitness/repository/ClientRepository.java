package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ClientRepository {

    private static final Logger LOGGER = LogManager.getLogger(ClientRepository.class);

    private final Map<Long, Client> clients = new HashMap<>();

    public void add(Client client) {
        clients.put(client.getId(), client);
        LOGGER.debug("Client added: {} ({})", client.getId(), client.getName());
    }

    public Client findById(long id) {
        Client client = clients.get(id);
        LOGGER.debug("Client lookup: {} -> {}", id, client != null ? client.getName() : "null");
        return client;
    }
}
