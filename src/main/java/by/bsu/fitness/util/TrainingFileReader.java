package by.bsu.fitness.util;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.exception.ValidationException;
import by.bsu.fitness.factory.TrainingBuilder;
import by.bsu.fitness.validator.DataValidator;
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

    private final DataValidator validator;

    public TrainingFileReader(DataValidator validator) {
        this.validator = validator;
        LOGGER.debug("TrainingFileReader created");
    }

    /**
     * Читает строки файла без обработки.
     */
    public List<String> readLines(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);
        LOGGER.debug("Read {} lines from file {}", lines.size(), filePath);
        return lines;
    }

    /**
     * Парсит строки в Training, но НЕ выбрасывает исключения.
     * Все ошибки записываются в errors.
     */
    public List<Training> parseTrainings(List<String> lines, List<String> errors) {
        List<Training> trainings = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            // Пропуск заголовка
            if (i == 0 && line.toLowerCase().startsWith("clientid")) {
                LOGGER.debug("Header line skipped: {}", line);
                continue;
            }

            try {
                // Валидация строки
                validator.validateLine(line);

                // Парсинг строки
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

    /**
     * Превращает CSV строку в Training.
     */
    private Training parseTraining(String line) throws ValidationException {
        try {
            String[] parts = line.split(",");

            long clientId = Long.parseLong(parts[0].trim());
            String coachName = parts[1].trim();
            TrainingType type = TrainingType.valueOf(parts[2].trim());
            LocalDateTime dateTime = LocalDateTime.parse(parts[3].trim());
            int duration = Integer.parseInt(parts[4].trim());
            TrainingState state = TrainingState.valueOf(parts[5].trim());

            Training training = new TrainingBuilder()
                    .clientId(clientId)
                    .coachName(coachName)
                    .type(type)
                    .dateTime(dateTime)
                    .durationMinutes(duration)
                    .state(state)
                    .build();

            LOGGER.debug("Training parsed: {}", training);
            return training;

        } catch (Exception e) {
            LOGGER.error("Failed to parse line: {}", line);
            throw new ValidationException("Invalid training format: " + line, e);
        }
    }
}
