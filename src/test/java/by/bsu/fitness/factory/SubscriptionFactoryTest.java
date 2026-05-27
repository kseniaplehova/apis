package by.bsu.fitness.factory;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.function.Supplier;

public class SubscriptionFactoryTest {

    private SubscriptionFactory factory;

    @BeforeMethod
    public void setUp() {
        factory = new SubscriptionFactory();
    }

    @DataProvider(name = "validTypes")
    public Object[][] validTypes() {
        return new Object[][]{
            {SubscriptionType.MONTHLY},
            {SubscriptionType.YEARLY},
            {SubscriptionType.VISIT_BASED}
        };
    }

    @Test(dataProvider = "validTypes")
    public void testCreateSubscriptionIsPresentForValidParams(SubscriptionType type) {
        Assert.assertTrue(factory.createSubscription(1L, type).isPresent());
    }

    @Test
    public void testCreateSubscriptionEmptyForNegativeClientId() {
        Assert.assertFalse(factory.createSubscription(-1L, SubscriptionType.MONTHLY).isPresent());
    }

    @Test
    public void testCreateSubscriptionEmptyForNullType() {
        Assert.assertFalse(factory.createSubscription(1L, null).isPresent());
    }

    @Test
    public void testCreateSubscriptionEmptyForZeroClientId() {
        Assert.assertFalse(factory.createSubscription(0L, SubscriptionType.MONTHLY).isPresent());
    }

    @Test
    public void testCreateSubscriptionMonthlyEndDate() {
        Subscription s = factory.createSubscription(1L, SubscriptionType.MONTHLY).get();
        Assert.assertEquals(s.getEndDate(), s.getStartDate().plusMonths(1));
    }

    @Test
    public void testCreateSubscriptionYearlyEndDate() {
        Subscription s = factory.createSubscription(1L, SubscriptionType.YEARLY).get();
        Assert.assertEquals(s.getEndDate(), s.getStartDate().plusYears(1));
    }

    @Test
    public void testCreateSubscriptionVisitBasedEndDate() {
        Subscription s = factory.createSubscription(1L, SubscriptionType.VISIT_BASED).get();
        Assert.assertEquals(s.getEndDate(), s.getStartDate().plusYears(5));
    }

    @Test
    public void testCreateSubscriptionStateIsActive() {
        Subscription s = factory.createSubscription(1L, SubscriptionType.MONTHLY).get();
        Assert.assertEquals(s.getState(), SubscriptionState.ACTIVE);
    }

    @Test
    public void testCreateSubscriptionClientIdSet() {
        Subscription s = factory.createSubscription(42L, SubscriptionType.MONTHLY).get();
        Assert.assertEquals(s.getClientId(), 42L);
    }

    @Test
    public void testCreateFromSupplierIsPresentForValidParams() {
        Optional<Subscription> result = factory.createFromSupplier(1L, () -> SubscriptionType.YEARLY);
        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void testCreateFromSupplierEmptyForNullSupplier() {
        Assert.assertFalse(factory.createFromSupplier(1L, null).isPresent());
    }

    @Test
    public void testCreateFromSupplierUsesProvidedType() {
        Subscription s = factory.createFromSupplier(1L, () -> SubscriptionType.YEARLY).get();
        Assert.assertEquals(s.getType(), SubscriptionType.YEARLY);
    }

    @Test
    public void testToSupplierWithTypeNotNull() {
        Supplier<Optional<Subscription>> supplier = factory.toSupplier(1L, SubscriptionType.MONTHLY);
        Assert.assertNotNull(supplier);
    }

    @Test
    public void testToSupplierWithTypeGetReturnsPresent() {
        Assert.assertTrue(factory.toSupplier(1L, SubscriptionType.MONTHLY).get().isPresent());
    }

    @Test
    public void testToSupplierWithTypeSupplierGetReturnsPresent() {
        Assert.assertTrue(factory.toSupplier(1L, () -> SubscriptionType.YEARLY).get().isPresent());
    }

    @Test
    public void testToSupplierWithTypeSupplierNotNull() {
        Assert.assertNotNull(factory.toSupplier(1L, () -> SubscriptionType.YEARLY));
    }

    @Test
    public void testToSupplierWithTypeGetReturnsInvalidForBadClientId() {
        Assert.assertFalse(factory.toSupplier(-1L, SubscriptionType.MONTHLY).get().isPresent());
    }
}
