package by.bsu.fitness.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientTest {

    private Client client;

    @BeforeMethod
    public void setUp() {
        client = new Client(1L, "Ксения", "+375-11-111-11-11");
    }

    @Test
    public void testGetIdReturnsCorrectValue() {
        Assert.assertEquals(client.getId(), 1L);
    }

    @Test
    public void testGetNameReturnsCorrectValue() {
        Assert.assertEquals(client.getName(), "Ксения");
    }

    @Test
    public void testGetPhoneReturnsCorrectValue() {
        Assert.assertEquals(client.getPhone(), "+375-11-111-11-11");
    }

    @Test
    public void testSetNameUpdatesName() {
        client.setName("Антон");
        Assert.assertEquals(client.getName(), "Антон");
    }

    @Test
    public void testSetPhoneUpdatesPhone() {
        client.setPhone("+375-22-222-22-22");
        Assert.assertEquals(client.getPhone(), "+375-22-222-22-22");
    }

    @Test
    public void testEqualsIdenticalClients() {
        Client same = new Client(1L, "Ксения", "+375-11-111-11-11");
        Assert.assertEquals(client, same);
    }

    @Test
    public void testNotEqualsDifferentId() {
        Client other = new Client(2L, "Ксения", "+375-11-111-11-11");
        Assert.assertNotEquals(client, other);
    }

    @Test
    public void testHashCodeConsistentWithEquals() {
        Client same = new Client(1L, "Ксения", "+375-11-111-11-11");
        Assert.assertEquals(client.hashCode(), same.hashCode());
    }

    @Test
    public void testToStringNotNull() {
        Assert.assertNotNull(client.toString());
    }

    @Test
    public void testToStringContainsId() {
        Assert.assertTrue(client.toString().contains("1"));
    }
}
