package by.bsu.fitness.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class SubscriptionTest {

    private Subscription subscription;
    private static final LocalDate START = LocalDate.of(2026, 1, 1);
    private static final LocalDate END   = LocalDate.of(2027, 1, 1);

    @BeforeMethod
    public void setUp() {
        subscription = new Subscription(10L, 1L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE);
    }

    @Test
    public void testGetIdReturnsCorrectValue() {
        Assert.assertEquals(subscription.getId(), 10L);
    }

    @Test
    public void testGetClientIdReturnsCorrectValue() {
        Assert.assertEquals(subscription.getClientId(), 1L);
    }

    @Test
    public void testGetTypeReturnsCorrectValue() {
        Assert.assertEquals(subscription.getType(), SubscriptionType.MONTHLY);
    }

    @Test
    public void testGetStartDateReturnsCorrectValue() {
        Assert.assertEquals(subscription.getStartDate(), START);
    }

    @Test
    public void testGetEndDateReturnsCorrectValue() {
        Assert.assertEquals(subscription.getEndDate(), END);
    }

    @Test
    public void testGetStateReturnsActive() {
        Assert.assertEquals(subscription.getState(), SubscriptionState.ACTIVE);
    }

    @Test
    public void testSetStateFrozen() {
        subscription.setState(SubscriptionState.FROZEN);
        Assert.assertEquals(subscription.getState(), SubscriptionState.FROZEN);
    }

    @Test
    public void testSetStateExpired() {
        subscription.setState(SubscriptionState.EXPIRED);
        Assert.assertEquals(subscription.getState(), SubscriptionState.EXPIRED);
    }

    @Test
    public void testEqualsIdenticalSubscriptions() {
        Subscription same = new Subscription(10L, 1L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE);
        Assert.assertEquals(subscription, same);
    }

    @Test
    public void testNotEqualsDifferentId() {
        Subscription other = new Subscription(99L, 1L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE);
        Assert.assertNotEquals(subscription, other);
    }

    @Test
    public void testHashCodeConsistentWithEquals() {
        Subscription same = new Subscription(10L, 1L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE);
        Assert.assertEquals(subscription.hashCode(), same.hashCode());
    }

    @Test
    public void testToStringNotNull() {
        Assert.assertNotNull(subscription.toString());
    }
}
