package by.bsu.fitness.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    private static final Logger LOGGER = LogManager.getLogger(Client.class);

    private final long id;
    private String name;
    private String phone;

    public Client(long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        LOGGER.debug("Client created: {}", this);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Changing client name from '{}' to '{}'", this.name, name);
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        LOGGER.debug("Changing client phone from '{}' to '{}'", this.phone, phone);
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Client client = (Client) o;

        if (id != client.id) {
            return false;
        }
        if (name != null ? !name.equals(client.name) : client.name != null) {
            return false;
        }
        return phone != null ? phone.equals(client.phone) : client.phone == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
