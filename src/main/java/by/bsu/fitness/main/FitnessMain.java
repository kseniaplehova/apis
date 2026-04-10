package by.bsu.fitness.main;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.exception.FitnessException;
import by.bsu.fitness.factory.SubscriptionFactory;
import by.bsu.fitness.factory.TrainingBuilder;
import by.bsu.fitness.observer.TrainingScheduleObservable;
import by.bsu.fitness.repository.ClientRepository;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.repository.TrainingRepository;
import by.bsu.fitness.service.NotificationService;
import by.bsu.fitness.service.SubscriptionService;
import by.bsu.fitness.service.TrainingService;
import by.bsu.fitness.util.TrainingFileReader;
import by.bsu.fitness.validator.DataValidator;
import by.bsu.fitness.validator.SubscriptionValidator;
import by.bsu.fitness.validator.TrainingValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FitnessMain {

    private static final Logger LOGGER = LogManager.getLogger(FitnessMain.class);

    public static void main(String[] args) {

        try {
            LOGGER.info("Application started");

            // -----------------------------
            // Репозитории
            // -----------------------------
            ClientRepository clientRepository = new ClientRepository();
            TrainingRepository trainingRepository = new TrainingRepository();
            SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

            // -----------------------------
            // Валидаторы
            // -----------------------------
            TrainingValidator trainingValidator = new TrainingValidator();
            SubscriptionValidator subscriptionValidator = new SubscriptionValidator();
            DataValidator dataValidator = new DataValidator();

            // -----------------------------
            // Фабрики
            // -----------------------------
            SubscriptionFactory subscriptionFactory = new SubscriptionFactory();

            // -----------------------------
            // Observable (Observer pattern)
            // -----------------------------
            TrainingScheduleObservable observable = new TrainingScheduleObservable();

            // -----------------------------
            // Сервисы
            // -----------------------------
            NotificationService notificationService = new NotificationService(observable);

            SubscriptionService subscriptionService =
                    new SubscriptionService(subscriptionRepository, subscriptionValidator, subscriptionFactory);

            TrainingService trainingService =
                    new TrainingService(trainingRepository, subscriptionRepository, trainingValidator, observable);

            TrainingFileReader fileReader = new TrainingFileReader(dataValidator);

            // -----------------------------
            // Создание клиентов
            // -----------------------------
            Client client1 = new Client(1L, "Ксения", "+44-111-222-333");
            Client client2 = new Client(2L, "Антон", "+44-444-555-666");

            clientRepository.add(client1);
            clientRepository.add(client2);

            // -----------------------------
            // Подписка клиентов на уведомления
            // -----------------------------
            notificationService.subscribeClient(client1);
            notificationService.subscribeClient(client2);

            // -----------------------------
            // Создание абонемента
            // -----------------------------
            subscriptionService.createSubscription(1L, SubscriptionType.MONTHLY);

            // -----------------------------
            // Создание тренировки вручную через Builder
            // -----------------------------
            Training training = new TrainingBuilder()
                    .clientId(1L)
                    .coachName("Иванов")
                    .type(TrainingType.GROUP)
                    .dateTime(LocalDateTime.now().plusDays(1))
                    .durationMinutes(60)
                    .state(TrainingState.PLANNED)
                    .build();

            trainingService.registerTraining(training);

            // -----------------------------
            // Чтение тренировок из файла (NIO)
            // -----------------------------
            List<String> errors = new ArrayList<>();

            List<String> lines = fileReader.readLines("resources/data/trainings.txt");
            List<Training> fileTrainings = fileReader.parseTrainings(lines, errors);

            // -----------------------------
            // Формирование отчёта
            // -----------------------------
            StringBuilder report = new StringBuilder();
            report.append("=== TRAINING REPORT ===\n");

            // Ошибки парсинга CSV
            for (String err : errors) {
                report.append(err).append("\n");
            }

            // Обработка валидных тренировок
            for (Training t : fileTrainings) {
                try {
                    trainingService.registerTraining(t);
                    report.append("SUCCESS: ").append(t).append("\n");
                } catch (Exception e) {
                    report.append("FAILED: ").append(t).append(" -> ").append(e.getMessage()).append("\n");
                }
            }

            // -----------------------------
            // Запись отчёта в файл
            // -----------------------------
            writeResultToFile(report.toString());

            LOGGER.info("Application finished successfully");

        } catch (Exception e) {
            LOGGER.error("Unexpected exception: {}", e.getMessage());
            writeResultToFile("Application failed: " + e.getMessage());
        }
    }

    // -----------------------------
    // Метод записи результата в файл
    // -----------------------------
    private static void writeResultToFile(String text) {
        try {
            Path path = Path.of("output/result.txt");

            Files.createDirectories(path.getParent());
            Files.write(path, text.getBytes(StandardCharsets.UTF_8));

            LOGGER.info("Result written to {}", path.toAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Failed to write result file: {}", e.getMessage());
        }
    }
}
