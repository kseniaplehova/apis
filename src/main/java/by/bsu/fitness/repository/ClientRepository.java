package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository {

    private static final Logger LOGGER = LogManager.getLogger(ClientRepository.class);

    private final List<Client> clients = new ArrayList<>();

    public void add(Client client) {
        clients.add(client);
        LOGGER.debug("Client added: {} ({})", client.getId(), client.getName());
    }

    public Optional<Client> findById(long id) {
        Optional<Client> result = clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        LOGGER.debug("Client lookup: {} -> {}", id, result.map(Client::getName).orElse("null"));
        return result;
    }
}
