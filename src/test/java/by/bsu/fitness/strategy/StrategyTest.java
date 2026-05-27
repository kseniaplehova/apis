package by.bsu.fitness.strategy;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.strategy.impl.GroupTrainingStrategy;
import by.bsu.fitness.strategy.impl.PersonalTrainingStrategy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class StrategyTest {

    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);
    private Training groupTraining;
    private Training personalTraining;

    @BeforeMethod
    public void setUp() {
        groupTraining = new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        personalTraining = new Training(2L, 2L, "Coach", TrainingType.PERSONAL, DT, 45, TrainingState.PLANNED);
    }

    @Test
    public void testGroupStrategyExecuteDoesNotChangeState() {
        new GroupTrainingStrategy().execute(groupTraining);
        Assert.assertEquals(groupTraining.getState(), TrainingState.PLANNED);
    }

    @Test
    public void testPersonalStrategyExecuteDoesNotChangeState() {
        new PersonalTrainingStrategy().execute(personalTraining);
        Assert.assertEquals(personalTraining.getState(), TrainingState.PLANNED);
    }

    @Test
    public void testGroupStrategyImplementsTrainingStrategy() {
        Assert.assertTrue(new GroupTrainingStrategy() instanceof TrainingStrategy);
    }

    @Test
    public void testPersonalStrategyImplementsTrainingStrategy() {
        Assert.assertTrue(new PersonalTrainingStrategy() instanceof TrainingStrategy);
    }
}
