package Core.Models;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String region;
    private static User loggedInUser;  // This stores the logged-in user

    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    private static final String USER_FILE = "users.txt";

    // Constructor
    public User(String id, String name, String email, String password, String role, String region , String customerType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.region = region;
    }

    // Constructor for simplified initialization
    public User() {}

    public User(String id, String name, String email, String password, String role, String region) {
    }

    // Register user and save details to a text file
    public boolean register() {
        if (name == null || email == null || password == null || region == null) {
            LOGGER.log(Level.WARNING, "Missing required fields for registration");
            return false;
        }


        File file = new File(USER_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("ID: " + id);
            writer.newLine();
            writer.write("Name: " + name);
            writer.newLine();
            writer.write("Email: " + email);
            writer.newLine();
            writer.write("Password: " + password);
            writer.newLine();
            writer.write("Role: " + role);
            writer.newLine();
            writer.write("Region: " + region);
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save user data to file", e);
            return false;
        }
    }

    public void handleBillPayment(String meterCode , int price) {
        System.out.println("Bill payment processed for user: " + name + " with meter code: " + meterCode);
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
        System.out.println("Logged in user: " + loggedInUser.getName());
    }

    public void login() {
        User.setLoggedInUser(this);
        User loggedInUser = User.getLoggedInUser();
        System.out.println("Current logged in user: " + (loggedInUser != null ? loggedInUser.getName() : "None"));
        System.out.println(this.getName() + " has successfully logged in.");
    }

    public boolean authenticate(String enteredPassword) {
        return this.password != null && this.password.equals(enteredPassword);
    }

    // Logout action
    public void logout() {
        System.out.println(name + " has successfully logged out.");
        loggedInUser = null; // Set logged-in user to null upon logout
    }

    // Getters and setters
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


}
// BY AHMED VOGO
