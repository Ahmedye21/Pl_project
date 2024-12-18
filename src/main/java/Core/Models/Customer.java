package Core.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Customer extends User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String region;

    // Updated constructor to accept all necessary parameters
    public Customer(String id, String name, String email, String password, String role, String region) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.region = region;
    }

    public Customer(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getter and setter methods for all attributes
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // Method to save customer information to a file (for example, "users.txt")
    public void saveCustomerInfo(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("ID: " + this.id + "\n");
            writer.write("Name: " + this.name + "\n");
            writer.write("Email: " + this.email + "\n");
            writer.write("Password: " + this.password + "\n");
            writer.write("Role: " + this.role + "\n");
            writer.write("Region: " + this.region + "\n");
            writer.write("----------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
