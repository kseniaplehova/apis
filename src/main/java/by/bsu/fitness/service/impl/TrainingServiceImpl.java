package by.bsu.fitness.service.impl;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.exception.ServiceException;
import by.bsu.fitness.observer.Observable;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.repository.TrainingRepository;
import by.bsu.fitness.service.TrainingService;
import by.bsu.fitness.strategy.TrainingStrategy;
import by.bsu.fitness.strategy.impl.GroupTrainingStrategy;
import by.bsu.fitness.strategy.impl.PersonalTrainingStrategy;
import by.bsu.fitness.template.GroupTrainingRegistration;
import by.bsu.fitness.template.PersonalTrainingRegistration;
import by.bsu.fitness.template.RegistrationTemplate;
import by.bsu.fitness.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LogManager.getLogger(TrainingServiceImpl.class);

    private final TrainingRepository repository;
    private final SubscriptionRepository subscriptionRepository;
    private final Validator<Training> validator;
    private final Observable observable;

    public TrainingServiceImpl(TrainingRepository repository,
                               SubscriptionRepository subscriptionRepository,
                               Validator<Training> validator,
                               Observable observable) {
        this.repository = repository;
        this.subscriptionRepository = subscriptionRepository;
        this.validator = validator;
        this.observable = observable;
        LOGGER.debug("TrainingServiceImpl created");
    }

    @Override
    public void registerTraining(Training training) throws ServiceException {
        if (!validator.validate(training)) {
            throw new ServiceException("Training validation failed");
        }

        Optional<Subscription> subscriptionOpt = subscriptionRepository.findByClientId(training.getClientId());
        if (subscriptionOpt.isEmpty()) {
            throw new ServiceException("No subscription for client " + training.getClientId());
        }
        Subscription subscription = subscriptionOpt.get();

        if (subscription.getState() == SubscriptionState.FROZEN) {
            throw new ServiceException("Subscription is frozen");
        }

        if (subscription.getEndDate().isBefore(training.getDateTime().toLocalDate())) {
            subscription.setState(SubscriptionState.EXPIRED);
            throw new ServiceException("Subscription expired");
        }

        try {
            repository.add(training);
        } catch (Exception e) {
            throw new ServiceException("Failed to save training", e);
        }

        TrainingStrategy strategy = chooseStrategy(training.getType());
        RegistrationTemplate template = createTemplate(training, strategy);
        template.register();
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
