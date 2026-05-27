package by.bsu.fitness.main;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.factory.SubscriptionFactory;
import by.bsu.fitness.factory.TrainingBuilder;
import by.bsu.fitness.observer.TrainingScheduleObservable;
import by.bsu.fitness.repository.ClientRepository;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.repository.TrainingRepository;
import by.bsu.fitness.service.NotificationService;
import by.bsu.fitness.service.SubscriptionService;
import by.bsu.fitness.service.TrainingService;
import by.bsu.fitness.service.impl.NotificationServiceImpl;
import by.bsu.fitness.service.impl.SubscriptionServiceImpl;
import by.bsu.fitness.service.impl.TrainingServiceImpl;
import by.bsu.fitness.util.TrainingFileReader;
import by.bsu.fitness.validator.DataValidator;
import by.bsu.fitness.validator.SubscriptionValidator;
import by.bsu.fitness.validator.TrainingValidator;
import by.bsu.fitness.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FitnessMain {

    private static final Logger LOGGER = LogManager.getLogger(FitnessMain.class);

    public static void main(String[] args) {

        try {
            LOGGER.info("Application started");

            // ── Repositories ──────────────────────────────────────────────────
            ClientRepository clientRepository = new ClientRepository();
            TrainingRepository trainingRepository = new TrainingRepository();
            SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

            // ── Validators (declared as Validator<T> interface) ───────────────
            Validator<Training> trainingValidator = new TrainingValidator();
            Validator<Subscription> subscriptionValidator = new SubscriptionValidator();
            Validator<String> dataValidator = new DataValidator();

            // ── Factory ───────────────────────────────────────────────────────
            SubscriptionFactory subscriptionFactory = new SubscriptionFactory();

            // ── Observable (Observer pattern) ─────────────────────────────────
            TrainingScheduleObservable observable = new TrainingScheduleObservable();

            // ── Services (declared as interfaces, instantiated as Impl) ───────
            NotificationService notificationService = new NotificationServiceImpl(observable);

            SubscriptionService subscriptionService =
                    new SubscriptionServiceImpl(subscriptionRepository, subscriptionValidator, subscriptionFactory);

            TrainingService trainingService =
                    new TrainingServiceImpl(trainingRepository, subscriptionRepository, trainingValidator, observable);

            TrainingFileReader fileReader = new TrainingFileReader(dataValidator);

            // ── Clients ───────────────────────────────────────────────────────
            Client client1 = new Client(1L, "Ксения", "+44-111-222-333");
            Client client2 = new Client(2L, "Антон", "+44-444-555-666");

            clientRepository.add(client1);
            clientRepository.add(client2);

            notificationService.subscribeClient(client1);
            notificationService.subscribeClient(client2);

            // ── Subscription via factory (Optional-based) ─────────────────────
            subscriptionService.createSubscription(1L, SubscriptionType.MONTHLY);

            // ── Factory: Supplier demo ────────────────────────────────────────
            Supplier<SubscriptionType> typeSupplier = () -> SubscriptionType.YEARLY;
            Supplier<Optional<Subscription>> lazySubscription =
                    subscriptionFactory.toSupplier(2L, typeSupplier);
            lazySubscription.get().ifPresent(s -> {
                subscriptionRepository.add(s);
                LOGGER.info("Lazy subscription created: {}", s);
            });

            // ── Elegant Builder: required fields enforced at compile time ──────
            Training training = TrainingBuilder.builder()
                    .clientId(1L)
                    .coachName("Иванов")
                    .type(TrainingType.GROUP)
                    .dateTime(LocalDateTime.now().plusDays(1))
                    .durationMinutes(60)
                    .state(TrainingState.PLANNED)
                    .build();

            // ── TrainingBuilder as Supplier<Training> ─────────────────────────
            Supplier<Training> trainingSupplier = TrainingBuilder.builder()
                    .clientId(1L)
                    .coachName("Петров")
                    .type(TrainingType.PERSONAL)
                    .durationMinutes(45)
                    .state(TrainingState.PLANNED);
            Training trainingFromSupplier = trainingSupplier.get();
            LOGGER.info("Training from Supplier: {}", trainingFromSupplier);

            trainingService.registerTraining(training);

            // ── Read trainings from file ───────────────────────────────────────
            List<String> errors = new ArrayList<>();
            List<String> lines = fileReader.readLines("resources/data/trainings.txt");
            List<Training> fileTrainings = fileReader.parseTrainings(lines, errors);

            // ── Build report ──────────────────────────────────────────────────
            StringBuilder report = new StringBuilder();
            report.append("=== TRAINING REPORT ===\n");

            for (String err : errors) {
                report.append(err).append("\n");
            }

            for (Training t : fileTrainings) {
                try {
                    trainingService.registerTraining(t);
                    report.append("SUCCESS: ").append(t).append("\n");
                } catch (Exception e) {
                    report.append("FAILED: ").append(t).append(" -> ").append(e.getMessage()).append("\n");
                }
            }

            writeResultToFile(report.toString());

            LOGGER.info("Application finished successfully");

        } catch (Exception e) {
            LOGGER.error("Unexpected exception: {}", e.getMessage());
            writeResultToFile("Application failed: " + e.getMessage());
        }
    }

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
