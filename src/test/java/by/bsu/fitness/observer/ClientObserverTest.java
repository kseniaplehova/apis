package by.bsu.fitness.observer;

import by.bsu.fitness.entity.Client;
import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class ClientObserverTest {

    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);
    private Client client;
    private ClientObserver observer;

    @BeforeMethod
    public void setUp() {
        client = new Client(1L, "Ксения", "+375-11-111-11-11");
        observer = new ClientObserver(client);
    }

    @Test
    public void testGetClientReturnsNotNull() {
        Assert.assertNotNull(observer.getClient());
    }

    @Test
    public void testGetClientReturnsCorrectId() {
        Assert.assertEquals(observer.getClient().getId(), 1L);
    }

    @Test
    public void testGetClientReturnsCorrectName() {
        Assert.assertEquals(observer.getClient().getName(), "Ксения");
    }

    @Test
    public void testGetClientReturnsSameReference() {
        Assert.assertSame(observer.getClient(), client);
    }

    @Test
    public void testClientObserverImplementsObserver() {
        Assert.assertTrue(observer instanceof Observer);
    }

    @Test
    public void testUpdateDoesNotChangeClient() {
        Training t = new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        observer.update(t);
        Assert.assertSame(observer.getClient(), client);
    }
}
