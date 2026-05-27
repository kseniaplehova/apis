package by.bsu.fitness.service;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.exception.ServiceException;
import by.bsu.fitness.observer.TrainingScheduleObservable;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.repository.TrainingRepository;
import by.bsu.fitness.service.impl.TrainingServiceImpl;
import by.bsu.fitness.validator.TrainingValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrainingServiceTest {

    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);

    private TrainingService service;
    private TrainingRepository trainingRepository;
    private SubscriptionRepository subscriptionRepository;

    @BeforeMethod
    public void setUp() {
        trainingRepository = new TrainingRepository();
        subscriptionRepository = new SubscriptionRepository();
        subscriptionRepository.add(new Subscription(1L, 1L, SubscriptionType.YEARLY,
                LocalDate.of(2025, 1, 1), LocalDate.of(2030, 1, 1), SubscriptionState.ACTIVE));
        service = new TrainingServiceImpl(trainingRepository, subscriptionRepository,
                new TrainingValidator(), new TrainingScheduleObservable());
    }

    @Test
    public void testRegisterTrainingSuccessAddsToRepository() throws ServiceException {
        service.registerTraining(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        Assert.assertEquals(trainingRepository.findAll().size(), 1);
    }

    @Test
    public void testRegisterPersonalTrainingSuccessAddsToRepository() throws ServiceException {
        service.registerTraining(new Training(2L, 1L, "Coach", TrainingType.PERSONAL, DT, 45, TrainingState.PLANNED));
        Assert.assertEquals(trainingRepository.findAll().size(), 1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRegisterTrainingNullThrowsServiceException() throws ServiceException {
        service.registerTraining(null);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRegisterTrainingNoSubscriptionThrowsServiceException() throws ServiceException {
        service.registerTraining(new Training(3L, 999L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRegisterTrainingFrozenSubscriptionThrowsServiceException() throws ServiceException {
        subscriptionRepository.add(new Subscription(2L, 2L, SubscriptionType.MONTHLY,
                LocalDate.of(2025, 1, 1), LocalDate.of(2030, 1, 1), SubscriptionState.FROZEN));
        service.registerTraining(new Training(4L, 2L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRegisterTrainingExpiredSubscriptionThrowsServiceException() throws ServiceException {
        subscriptionRepository.add(new Subscription(3L, 3L, SubscriptionType.MONTHLY,
                LocalDate.of(2020, 1, 1), LocalDate.of(2021, 1, 1), SubscriptionState.ACTIVE));
        service.registerTraining(new Training(5L, 3L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
    }
}
