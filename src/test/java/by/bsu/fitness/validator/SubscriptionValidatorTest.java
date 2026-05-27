package by.bsu.fitness.validator;

import by.bsu.fitness.entity.Subscription;
import by.bsu.fitness.entity.SubscriptionState;
import by.bsu.fitness.entity.SubscriptionType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class SubscriptionValidatorTest {

    private SubscriptionValidator validator;
    private static final LocalDate START = LocalDate.of(2026, 1, 1);
    private static final LocalDate END   = LocalDate.of(2027, 1, 1);

    @BeforeMethod
    public void setUp() {
        validator = new SubscriptionValidator();
    }

    @Test
    public void testValidSubscriptionReturnsTrue() {
        Subscription s = new Subscription(1L, 1L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE);
        Assert.assertTrue(validator.validate(s));
    }

    @Test
    public void testNullSubscriptionReturnsFalse() {
        Assert.assertFalse(validator.validate(null));
    }

    @Test
    public void testNegativeClientIdReturnsFalse() {
        Subscription s = new Subscription(1L, -1L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE);
        Assert.assertFalse(validator.validate(s));
    }

    @Test
    public void testZeroClientIdReturnsFalse() {
        Subscription s = new Subscription(1L, 0L, SubscriptionType.MONTHLY, START, END, SubscriptionState.ACTIVE);
        Assert.assertFalse(validator.validate(s));
    }

    @Test
    public void testNullStartDateReturnsFalse() {
        Subscription s = new Subscription(1L, 1L, SubscriptionType.MONTHLY, null, END, SubscriptionState.ACTIVE);
        Assert.assertFalse(validator.validate(s));
    }

    @Test
    public void testNullEndDateReturnsFalse() {
        Subscription s = new Subscription(1L, 1L, SubscriptionType.MONTHLY, START, null, SubscriptionState.ACTIVE);
        Assert.assertFalse(validator.validate(s));
    }

    @Test
    public void testEndBeforeStartReturnsFalse() {
        Subscription s = new Subscription(1L, 1L, SubscriptionType.MONTHLY, END, START, SubscriptionState.ACTIVE);
        Assert.assertFalse(validator.validate(s));
    }

    @Test
    public void testValidatorImplementsValidatorInterface() {
        Assert.assertTrue(validator instanceof Validator);
    }
}
