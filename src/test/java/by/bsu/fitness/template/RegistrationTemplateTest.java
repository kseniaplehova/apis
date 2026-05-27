package by.bsu.fitness.template;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.observer.Observable;
import by.bsu.fitness.observer.Observer;
import by.bsu.fitness.strategy.impl.GroupTrainingStrategy;
import by.bsu.fitness.strategy.impl.PersonalTrainingStrategy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegistrationTemplateTest {

    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);
    private Training groupTraining;
    private Training personalTraining;

    @BeforeMethod
    public void setUp() {
        groupTraining = new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        personalTraining = new Training(2L, 2L, "Coach", TrainingType.PERSONAL, DT, 45, TrainingState.PLANNED);
    }

    @Test
    public void testGroupRegistrationNotifiesObservers() {
        AtomicBoolean notified = new AtomicBoolean(false);
        Observable observable = new Observable() {
            public void addObserver(Observer o) { }
            public void removeObserver(Observer o) { }
            public void notifyObservers(Training t) { notified.set(true); }
        };
        new GroupTrainingRegistration(groupTraining, new GroupTrainingStrategy(), observable).register();
        Assert.assertTrue(notified.get());
    }

    @Test
    public void testPersonalRegistrationNotifiesObservers() {
        AtomicBoolean notified = new AtomicBoolean(false);
        Observable observable = new Observable() {
            public void addObserver(Observer o) { }
            public void removeObserver(Observer o) { }
            public void notifyObservers(Training t) { notified.set(true); }
        };
        new PersonalTrainingRegistration(personalTraining, new PersonalTrainingStrategy(), observable).register();
        Assert.assertTrue(notified.get());
    }

    @Test
    public void testGroupRegistrationNotifiesWithCorrectTrainingId() {
        AtomicBoolean correct = new AtomicBoolean(false);
        Observable observable = new Observable() {
            public void addObserver(Observer o) { }
            public void removeObserver(Observer o) { }
            public void notifyObservers(Training t) { correct.set(t.getId() == 1L); }
        };
        new GroupTrainingRegistration(groupTraining, new GroupTrainingStrategy(), observable).register();
        Assert.assertTrue(correct.get());
    }

    @Test
    public void testPersonalRegistrationNotifiesWithCorrectTrainingId() {
        AtomicBoolean correct = new AtomicBoolean(false);
        Observable observable = new Observable() {
            public void addObserver(Observer o) { }
            public void removeObserver(Observer o) { }
            public void notifyObservers(Training t) { correct.set(t.getId() == 2L); }
        };
        new PersonalTrainingRegistration(personalTraining, new PersonalTrainingStrategy(), observable).register();
        Assert.assertTrue(correct.get());
    }
}
