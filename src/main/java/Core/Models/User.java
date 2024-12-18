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
    private String customerType; // Added customerType field for users
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
        this.customerType = (customerType != null) ? customerType : "New";
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
        if (customerType == null) {
            customerType = "New";
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
            writer.write("CustomerType: " + customerType);
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save user data to file", e);
            return false;
        }
    }

    public User searchUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            String id = null, name = null, email = null, filePassword = null, role = null, region = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID:")) {
                    id = line.substring(3).trim();
                } else if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Email:")) {
                    email = line.substring(6).trim();
                } else if (line.startsWith("Password:")) {
                    filePassword = line.substring(9).trim();
                } else if (line.startsWith("Role:")) {
                    role = line.substring(5).trim();
                } else if (line.startsWith("Region:")) {
                    region = line.substring(7).trim();
                } else if (line.startsWith("CustomerType:")) {
                    customerType = line.substring(13).trim();
                }

                // Check if the current user matches the search criteria
                if (name != null && filePassword != null) {
                    if (name.equals(username) && filePassword.equals(password)) {
                        return new User(id, name, email, filePassword, role, region, customerType); // Return with customerType
                    }
                    // Reset fields for the next user
                    id = name = email = filePassword = role = region = customerType = null;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error searching user in file", e);
        }
        return null;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
