package by.bsu.fitness.service;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.entity.Training;
import by.bsu.fitness.observer.ClientObserver;
import by.bsu.fitness.observer.Observable;
import by.bsu.fitness.observer.Observer;
import by.bsu.fitness.service.impl.NotificationServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationServiceTest {

    private NotificationService service;
    private AtomicInteger addCount;
    private AtomicInteger removeCount;

    @BeforeMethod
    public void setUp() {
        addCount = new AtomicInteger(0);
        removeCount = new AtomicInteger(0);
        Observable observable = new Observable() {
            public void addObserver(Observer o) { addCount.incrementAndGet(); }
            public void removeObserver(Observer o) { removeCount.incrementAndGet(); }
            public void notifyObservers(Training t) { }
        };
        service = new NotificationServiceImpl(observable);
    }

    @Test
    public void testSubscribeClientCallsAddObserverOnce() {
        service.subscribeClient(new Client(1L, "Name", "+1"));
        Assert.assertEquals(addCount.get(), 1);
    }

    @Test
    public void testSubscribeClientTwiceCallsAddObserverTwice() {
        service.subscribeClient(new Client(1L, "A", "+1"));
        service.subscribeClient(new Client(2L, "B", "+2"));
        Assert.assertEquals(addCount.get(), 2);
    }

    @Test
    public void testUnsubscribeClientCallsRemoveObserverOnce() {
        ClientObserver observer = new ClientObserver(new Client(1L, "Name", "+1"));
        service.unsubscribeClient(observer);
        Assert.assertEquals(removeCount.get(), 1);
    }

    @Test
    public void testServiceImplementsNotificationService() {
        Assert.assertTrue(service instanceof NotificationService);
    }
}
