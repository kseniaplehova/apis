package by.bsu.fitness.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IdGeneratorTest {

    @Test
    public void testNextIdReturnsPositiveValue() {
        Assert.assertTrue(IdGenerator.nextId() > 0);
    }

    @Test
    public void testNextIdMonotonicallyIncreases() {
        long first = IdGenerator.nextId();
        long second = IdGenerator.nextId();
        Assert.assertTrue(second > first);
    }

    @Test
    public void testNextIdIsNotZero() {
        Assert.assertNotEquals(IdGenerator.nextId(), 0L);
    }

    @Test
    public void testSuccessiveIdsAreDifferent() {
        long first = IdGenerator.nextId();
        long second = IdGenerator.nextId();
        Assert.assertNotEquals(first, second);
    }
}
