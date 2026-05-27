package by.bsu.fitness.specification;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrainingSpecificationTest {

    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);
    private Training training;

    @BeforeMethod
    public void setUp() {
        training = new Training(1L, 5L, "Coach Smith", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
    }

    @Test
    public void testByClientIdMatchesCorrectClient() {
        Assert.assertTrue(new TrainingByClientIdSpecification(5L).match(training));
    }

    @Test
    public void testByClientIdDoesNotMatchOtherClient() {
        Assert.assertFalse(new TrainingByClientIdSpecification(99L).match(training));
    }

    @Test
    public void testByTypeMatchesGroupType() {
        Assert.assertTrue(new TrainingByTypeSpecification(TrainingType.GROUP).match(training));
    }

    @Test
    public void testByTypeDoesNotMatchPersonalType() {
        Assert.assertFalse(new TrainingByTypeSpecification(TrainingType.PERSONAL).match(training));
    }

    @Test
    public void testByDateMatchesCorrectDate() {
        Assert.assertTrue(new TrainingByDateSpecification(LocalDate.of(2026, 6, 1)).match(training));
    }

    @Test
    public void testByDateDoesNotMatchOtherDate() {
        Assert.assertFalse(new TrainingByDateSpecification(LocalDate.of(2025, 1, 1)).match(training));
    }

    @Test
    public void testByDurationMatchesCorrectDuration() {
        Assert.assertTrue(new TrainingByDurationSpecification(60).match(training));
    }

    @Test
    public void testByDurationDoesNotMatchOtherDuration() {
        Assert.assertFalse(new TrainingByDurationSpecification(90).match(training));
    }

    @Test
    public void testByCoachNameMatchesCorrectName() {
        Assert.assertTrue(new TrainingByCoachNameSpecification("Coach Smith").match(training));
    }

    @Test
    public void testByCoachNameDoesNotMatchOtherName() {
        Assert.assertFalse(new TrainingByCoachNameSpecification("Wrong Coach").match(training));
    }
}
