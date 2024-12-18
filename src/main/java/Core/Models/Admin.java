package Core.Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Admin extends User {

    public Admin(String id, String name, String email, String password, String role, String region ) {
        super(id, name, email, password, role, region);
    }

    public Admin() {}

    public Set<Customer> loadUsers() {
        Set<Customer> customers = new HashSet<>();
        String id = null;
        String name = null;
        String email = null;
        String role = null;
        String password = null;  // Add password field
        String region = null;    // Add region field

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Email:")) {
                    email = line.substring(6).trim();
                } else if (line.startsWith("Role:")) {
                    role = line.substring(5).trim(); // Capture the role (i.e., customerType)
                } else if (line.startsWith("Password:")) {
                    password = line.substring(9).trim();  // Capture the password
                } else if (line.startsWith("Region:")) {
                    region = line.substring(7).trim();  // Capture the region
                }

                // When all information is gathered, add the customer
                if (name != null && email != null && role != null && password != null && region != null) {
                    // Generate ID based on the current size of the set
                    id = String.valueOf(customers.size() + 1);  // Can be improved if you want more reliable ID generation

                    // Add the customer to the set
                    customers.add(new Customer(id, name, email, password, role, region));

                    // Reset variables for the next user
                    name = null;
                    email = null;
                    role = null;
                    password = null;
                    region = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }
    // Method to load regions from the "users.txt" file
    public Set<String> loadRegions() {
        Set<String> regions = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Region:")) {
                    String region = line.substring(7).trim();
                    regions.add(region);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return regions;
    }

    // Admin-specific methods for reports and user management
    public void viewReports() {
        System.out.println("Admin is viewing reports...");
        // Implement logic for viewing reports
    }

    public void manageUsers() {
        System.out.println("Admin is managing users...");
        // Implement logic for managing users
    }

    // Additional admin-specific behavior can be added here
}
