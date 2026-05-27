package by.bsu.fitness.service;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import by.bsu.fitness.exception.ServiceException;
import by.bsu.fitness.factory.SubscriptionFactory;
import by.bsu.fitness.repository.SubscriptionRepository;
import by.bsu.fitness.service.impl.SubscriptionServiceImpl;
import by.bsu.fitness.validator.SubscriptionValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SubscriptionServiceTest {

    private SubscriptionService service;
    private SubscriptionRepository repository;

    @BeforeMethod
    public void setUp() {
        repository = new SubscriptionRepository();
        service = new SubscriptionServiceImpl(repository, new SubscriptionValidator(), new SubscriptionFactory());
    }

    @Test
    public void testCreateSubscriptionReturnsNonNull() throws ServiceException {
        Assert.assertNotNull(service.createSubscription(1L, SubscriptionType.MONTHLY));
    }

    @Test
    public void testCreateSubscriptionReturnsCorrectClientId() throws ServiceException {
        Assert.assertEquals(service.createSubscription(1L, SubscriptionType.MONTHLY).getClientId(), 1L);
    }

    @Test
    public void testCreateSubscriptionReturnsActiveState() throws ServiceException {
        Assert.assertEquals(service.createSubscription(1L, SubscriptionType.MONTHLY).getState(), SubscriptionState.ACTIVE);
    }

    @Test
    public void testCreateSubscriptionYearlyTypeReturnsCorrectType() throws ServiceException {
        Assert.assertEquals(service.createSubscription(1L, SubscriptionType.YEARLY).getType(), SubscriptionType.YEARLY);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateSubscriptionInvalidClientIdThrowsServiceException() throws ServiceException {
        service.createSubscription(0L, SubscriptionType.MONTHLY);
    }

    @Test
    public void testFreezeChangesStateFrozen() throws ServiceException {
        Subscription created = service.createSubscription(1L, SubscriptionType.MONTHLY);
        service.freeze(created.getId());
        Assert.assertEquals(repository.findById(created.getId()).get().getState(), SubscriptionState.FROZEN);
    }

    @Test
    public void testActivateChangesStateActive() throws ServiceException {
        Subscription created = service.createSubscription(1L, SubscriptionType.MONTHLY);
        service.freeze(created.getId());
        service.activate(created.getId());
        Assert.assertEquals(repository.findById(created.getId()).get().getState(), SubscriptionState.ACTIVE);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFreezeNotFoundThrowsServiceException() throws ServiceException {
        service.freeze(999L);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testActivateNotFoundThrowsServiceException() throws ServiceException {
        service.activate(999L);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCheckExpirationNotFoundThrowsServiceException() throws ServiceException {
        service.checkExpiration(999L);
    }

    @Test
    public void testCheckExpirationFutureEndDateKeepsActiveState() throws ServiceException {
        Subscription created = service.createSubscription(1L, SubscriptionType.YEARLY);
        service.checkExpiration(created.getId());
        Assert.assertEquals(repository.findById(created.getId()).get().getState(), SubscriptionState.ACTIVE);
    }
}
