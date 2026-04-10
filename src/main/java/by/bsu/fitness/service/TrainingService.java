package by.bsu.fitness.service;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.exception.ServiceException;
import by.bsu.fitness.exception.ValidationException;
import by.bsu.fitness.observer.Observable;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.repository.TrainingRepository;
import by.bsu.fitness.strategy.GroupTrainingStrategy;
import by.bsu.fitness.strategy.PersonalTrainingStrategy;
import by.bsu.fitness.strategy.TrainingStrategy;
import by.bsu.fitness.template.GroupTrainingRegistration;
import by.bsu.fitness.template.PersonalTrainingRegistration;
import by.bsu.fitness.template.RegistrationTemplate;
import by.bsu.fitness.validator.TrainingValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingService {

    private static final Logger LOGGER = LogManager.getLogger(TrainingService.class);

    private final TrainingRepository repository;
    private final SubscriptionRepository subscriptionRepository;
    private final TrainingValidator validator;
    private final Observable observable;

    public TrainingService(TrainingRepository repository,
                           SubscriptionRepository subscriptionRepository,
                           TrainingValidator validator,
                           Observable observable) {
        this.repository = repository;
        this.subscriptionRepository = subscriptionRepository;
        this.validator = validator;
        this.observable = observable;
        LOGGER.debug("TrainingService created");
    }

    public void registerTraining(Training training) throws ServiceException {
        try {
            validator.validate(training);

            Subscription subscription = subscriptionRepository.findByClientId(training.getClientId());
            if (subscription == null) throw new ServiceException("No subscription for client");

            if (subscription.getState() == SubscriptionState.FROZEN)
                throw new ServiceException("Subscription is frozen");

            if (subscription.getEndDate().isBefore(training.getDateTime().toLocalDate())) {
                subscription.setState(SubscriptionState.EXPIRED);
                throw new ServiceException("Subscription expired");
            }

            repository.add(training);

            TrainingStrategy strategy = chooseStrategy(training.getType());
            RegistrationTemplate template = createTemplate(training, strategy);

            template.register();

        } catch (Exception e) {
            throw new ServiceException("Training registration failed", e);
        }
    }

    private TrainingStrategy chooseStrategy(TrainingType type) {
        return type == TrainingType.GROUP
                ? new GroupTrainingStrategy()
                : new PersonalTrainingStrategy();
    }

    private RegistrationTemplate createTemplate(Training training, TrainingStrategy strategy) {
        return training.getType() == TrainingType.GROUP
                ? new GroupTrainingRegistration(training, strategy, observable)
                : new PersonalTrainingRegistration(training, strategy, observable);
    }
}
