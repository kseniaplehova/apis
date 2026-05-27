package by.bsu.fitness.validator;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.factory.TrainingBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class TrainingValidatorTest {

    private TrainingValidator validator;
    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);

    @BeforeMethod
    public void setUp() {
        validator = new TrainingValidator();
    }

    @Test
    public void testValidTrainingReturnsTrue() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Иванов").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertTrue(validator.validate(t));
    }

    @Test
    public void testNullTrainingReturnsFalse() {
        Assert.assertFalse(validator.validate(null));
    }

    @Test
    public void testNegativeClientIdReturnsFalse() {
        Training t = new TrainingBuilder()
                .clientId(-1L).coachName("Иванов").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertFalse(validator.validate(t));
    }

    @Test
    public void testZeroClientIdReturnsFalse() {
        Training t = new TrainingBuilder()
                .clientId(0L).coachName("Иванов").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertFalse(validator.validate(t));
    }

    @Test
    public void testZeroDurationReturnsFalse() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Иванов").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(0).state(TrainingState.PLANNED)
                .build();
        Assert.assertFalse(validator.validate(t));
    }

    @Test
    public void testNegativeDurationReturnsFalse() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Иванов").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(-10).state(TrainingState.PLANNED)
                .build();
        Assert.assertFalse(validator.validate(t));
    }

    @Test
    public void testEmptyCoachNameReturnsFalse() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertFalse(validator.validate(t));
    }

    @Test
    public void testNullCoachNameReturnsFalse() {
        Training t = new Training(1L, 1L, null, TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        Assert.assertFalse(validator.validate(t));
    }

    @Test
    public void testValidatorImplementsValidatorInterface() {
        Assert.assertTrue(validator instanceof Validator);
    }
}
