package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class SubscriptionRepositoryTest {

    private SubscriptionRepository repository;
    private static final LocalDate START = LocalDate.of(2026, 1, 1);
    private static final LocalDate END   = LocalDate.of(2027, 1, 1);

    @BeforeMethod
    public void setUp() {
        repository = new SubscriptionRepository();
    }

    @Test
    public void testFindByIdAfterAddReturnsPresent() {
        repository.add(new Subscription(10L, 1L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE));
        Assert.assertTrue(repository.findById(10L).isPresent());
    }

    @Test
    public void testFindByIdAbsentReturnsEmpty() {
        Assert.assertFalse(repository.findById(999L).isPresent());
    }

    @Test
    public void testFindByIdReturnsCorrectType() {
        repository.add(new Subscription(1L, 1L, SubscriptionType.YEARLY, START, END, SubscriptionState.ACTIVE));
        Assert.assertEquals(repository.findById(1L).get().getType(), SubscriptionType.YEARLY);
    }

    @Test
    public void testFindByClientIdAfterAddReturnsPresent() {
        repository.add(new Subscription(1L, 5L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE));
        Assert.assertTrue(repository.findByClientId(5L).isPresent());
    }

    @Test
    public void testFindByClientIdAbsentReturnsEmpty() {
        Assert.assertFalse(repository.findByClientId(999L).isPresent());
    }

    @Test
    public void testFindByClientIdReturnsCorrectClientId() {
        repository.add(new Subscription(1L, 7L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE));
        Assert.assertEquals(repository.findByClientId(7L).get().getClientId(), 7L);
    }

    @Test
    public void testFindByClientIdReturnsCorrectState() {
        repository.add(new Subscription(1L, 2L, SubscriptionType.MONTHLY, START, END, SubscriptionState.FROZEN));
        Assert.assertEquals(repository.findByClientId(2L).get().getState(), SubscriptionState.FROZEN);
    }
}
