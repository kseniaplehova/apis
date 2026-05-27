package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Client;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientRepositoryTest {

    private ClientRepository repository;

    @BeforeMethod
    public void setUp() {
        repository = new ClientRepository();
    }

    @Test
    public void testFindByIdAfterAddReturnsPresent() {
        repository.add(new Client(1L, "Ксения", "+1"));
        Assert.assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    public void testFindByIdReturnsCorrectClient() {
        repository.add(new Client(2L, "Антон", "+2"));
        Assert.assertEquals(repository.findById(2L).get().getName(), "Антон");
    }

    @Test
    public void testFindByIdAbsentClientReturnsEmpty() {
        Assert.assertFalse(repository.findById(999L).isPresent());
    }

    @Test
    public void testFindByIdReturnsCorrectId() {
        repository.add(new Client(5L, "Test", "+5"));
        Assert.assertEquals(repository.findById(5L).get().getId(), 5L);
    }

    @Test
    public void testAddMultipleClientsCanFindSecond() {
        repository.add(new Client(1L, "First", "+1"));
        repository.add(new Client(2L, "Second", "+2"));
        Assert.assertTrue(repository.findById(2L).isPresent());
    }

    @Test
    public void testFindByIdReturnsPhone() {
        repository.add(new Client(3L, "Name", "+375-11-111-11-11"));
        Assert.assertEquals(repository.findById(3L).get().getPhone(), "+375-11-111-11-11");
    }
}
