package by.bsu.fitness.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class IdGenerator {

    private static final Logger LOGGER = LogManager.getLogger(IdGenerator.class);
    private static long currentId = 0L;

    private IdGenerator() {
        LOGGER.debug("IdGenerator instantiated");
    }

    public static synchronized long nextId() {
        currentId++;
        LOGGER.debug("Generated new id: {}", currentId);
        return currentId;
    }
}
