package by.bsu.fitness.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class TrainingTest {

    private Training training;
    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);

    @BeforeMethod
    public void setUp() {
        training = new Training(5L, 1L, "Иванов", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
    }

    @Test
    public void testGetIdReturnsCorrectValue() {
        Assert.assertEquals(training.getId(), 5L);
    }

    @Test
    public void testGetClientIdReturnsCorrectValue() {
        Assert.assertEquals(training.getClientId(), 1L);
    }

    @Test
    public void testGetCoachNameReturnsCorrectValue() {
        Assert.assertEquals(training.getCoachName(), "Иванов");
    }

    @Test
    public void testGetTypeGroup() {
        Assert.assertEquals(training.getType(), TrainingType.GROUP);
    }

    @Test
    public void testGetDateTimeReturnsCorrectValue() {
        Assert.assertEquals(training.getDateTime(), DT);
    }

    @Test
    public void testGetDurationMinutesReturnsCorrectValue() {
        Assert.assertEquals(training.getDurationMinutes(), 60);
    }

    @Test
    public void testGetStatePlanned() {
        Assert.assertEquals(training.getState(), TrainingState.PLANNED);
    }

    @Test
    public void testSetStateActive() {
        training.setState(TrainingState.ACTIVE);
        Assert.assertEquals(training.getState(), TrainingState.ACTIVE);
    }

    @Test
    public void testSetStateFinished() {
        training.setState(TrainingState.FINISHED);
        Assert.assertEquals(training.getState(), TrainingState.FINISHED);
    }

    @Test
    public void testSetStateCanceled() {
        training.setState(TrainingState.CANCELED);
        Assert.assertEquals(training.getState(), TrainingState.CANCELED);
    }

    @Test
    public void testEqualsIdenticalTrainings() {
        Training same = new Training(5L, 1L, "Иванов", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        Assert.assertEquals(training, same);
    }

    @Test
    public void testNotEqualsDifferentId() {
        Training other = new Training(99L, 1L, "Иванов", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        Assert.assertNotEquals(training, other);
    }

    @Test
    public void testHashCodeConsistentWithEquals() {
        Training same = new Training(5L, 1L, "Иванов", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        Assert.assertEquals(training.hashCode(), same.hashCode());
    }

    @Test
    public void testToStringNotNull() {
        Assert.assertNotNull(training.toString());
    }
}
