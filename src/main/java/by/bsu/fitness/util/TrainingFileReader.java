package by.bsu.fitness.util;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.exception.ValidationException;
import by.bsu.fitness.factory.TrainingBuilder;
import by.bsu.fitness.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainingFileReader {

    private static final Logger LOGGER = LogManager.getLogger(TrainingFileReader.class);

    private final Validator<String> validator;

    public TrainingFileReader(Validator<String> validator) {
        this.validator = validator;
        LOGGER.debug("TrainingFileReader created");
    }

    public List<String> readLines(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);
        LOGGER.debug("Read {} lines from file {}", lines.size(), filePath);
        return lines;
    }

    public List<Training> parseTrainings(List<String> lines, List<String> errors) {
        List<Training> trainings = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (i == 0 && line.toLowerCase().startsWith("clientid")) {
                LOGGER.debug("Header line skipped: {}", line);
                continue;
            }

            if (!validator.validate(line)) {
                String error = "FAILED: " + line + " -> invalid data format";
                errors.add(error);
                LOGGER.error("Invalid line skipped: {}", line);
                continue;
            }

            try {
                Training training = parseTraining(line);
                trainings.add(training);
                LOGGER.debug("Training parsed and added: {}", training.getId());
            } catch (Exception e) {
                String error = "FAILED: " + line + " -> " + e.getMessage();
                errors.add(error);
                LOGGER.error("Invalid line skipped: {}", error);
            }
        }

        return trainings;
    }

    private Training parseTraining(String line) throws ValidationException {
        try {
            String[] parts = line.split(",");

            long clientId = Long.parseLong(parts[0].trim());
            String coachName = parts[1].trim();
            TrainingType type = TrainingType.valueOf(parts[2].trim());
            LocalDateTime dateTime = LocalDateTime.parse(parts[3].trim());
            int duration = Integer.parseInt(parts[4].trim());
            TrainingState state = TrainingState.valueOf(parts[5].trim());

            return new TrainingBuilder()
                    .clientId(clientId)
                    .coachName(coachName)
                    .type(type)
                    .dateTime(dateTime)
                    .durationMinutes(duration)
                    .state(state)
                    .build();

        } catch (Exception e) {
            LOGGER.error("Failed to parse line: {}", line);
            throw new ValidationException("Invalid training format: " + line, e);
        }
    }
}
