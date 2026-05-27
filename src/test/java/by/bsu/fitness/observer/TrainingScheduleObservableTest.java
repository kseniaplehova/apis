package by.bsu.fitness.observer;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TrainingScheduleObservableTest {

    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);
    private TrainingScheduleObservable observable;
    private Training training;

    @BeforeMethod
    public void setUp() {
        observable = new TrainingScheduleObservable();
        training = new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
    }

    @Test
    public void testNotifyCallsUpdate() {
        AtomicBoolean called = new AtomicBoolean(false);
        observable.addObserver(t -> called.set(true));
        observable.notifyObservers(training);
        Assert.assertTrue(called.get());
    }

    @Test
    public void testNotifyPassesCorrectTrainingId() {
        AtomicBoolean correct = new AtomicBoolean(false);
        observable.addObserver(t -> correct.set(t.getId() == 1L));
        observable.notifyObservers(training);
        Assert.assertTrue(correct.get());
    }

    @Test
    public void testRemoveObserverPreventsNotification() {
        AtomicBoolean called = new AtomicBoolean(false);
        Observer obs = t -> called.set(true);
        observable.addObserver(obs);
        observable.removeObserver(obs);
        observable.notifyObservers(training);
        Assert.assertFalse(called.get());
    }

    @Test
    public void testTwoObserversBothNotified() {
        AtomicInteger count = new AtomicInteger(0);
        observable.addObserver(t -> count.incrementAndGet());
        observable.addObserver(t -> count.incrementAndGet());
        observable.notifyObservers(training);
        Assert.assertEquals(count.get(), 2);
    }

    @Test
    public void testObservableImplementsObservable() {
        Assert.assertTrue(observable instanceof Observable);
    }

    @Test
    public void testNoObserversNoNotification() {
        AtomicBoolean called = new AtomicBoolean(false);
        observable.notifyObservers(training);
        Assert.assertFalse(called.get());
    }
}
