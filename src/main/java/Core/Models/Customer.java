package Core.Models;

public class Customer extends User {
    public Customer(String customerId, String name, String email) {
        this.setId(customerId);
        this.setName(name);
        this.setEmail(email);
        this.setRole("Customer");
    }

    public Customer(int customerId, String name, String email) {
        this.setId(String.valueOf(customerId));
        this.setName(name);
        this.setEmail(email);
        this.setRole("Customer");
    }
}