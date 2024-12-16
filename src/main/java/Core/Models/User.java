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

    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    private static final String USER_FILE = "users.txt";

    // Constructor
    public User(String id, String name, String email, String password, String role, String region) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.region = region;
    }

    // Constructor for simplified initialization
    public User() {}

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

    // Search for a user in the text file by username and password
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
                }

                // Check if the current user matches the search criteria
                if (name != null && filePassword != null) {
                    if (name.equals(username) && filePassword.equals(password)) {
                        return new User(id, name, email, filePassword, role, region);
                    }
                    // Reset fields for the next user
                    id = name = email = filePassword = role = region = null;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error searching user in file", e);
        }
        return null; // Return null if user is not found
    }

    // Authenticate the user with the provided password
    public boolean authenticate(String enteredPassword) {
        return this.password != null && this.password.equals(enteredPassword);
    }

    // Login action
    public void login() {
        System.out.println(name + " has successfully logged in.");
    }

    // Logout action
    public void logout() {
        System.out.println(name + " has successfully logged out.");
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
