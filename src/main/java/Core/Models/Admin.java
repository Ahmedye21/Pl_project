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

    public Set<Customer> loadBills(String region) {
        Set<Customer> bills = new HashSet<>();
        String id = null;
        String name = null;
        double amount = 0.0;
        String billNumber = null;
        String meterCode = null;
        String userRegion = null;
        String email = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("bills.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Amount: $")) {
                    try {
                        amount = Double.parseDouble(line.substring(9).trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing amount: " + line);
                    }
                } else if (line.startsWith("Bill Number:")) {
                    billNumber = line.substring(12).trim();
                } else if (line.startsWith("Meter Code:")) {
                    meterCode = line.substring(11).trim();
                } else if (line.startsWith("Region:")) {
                    userRegion = line.substring(7).trim();
                } else if (line.startsWith("Customer Email:")) {
                    email = line.substring(15).trim();
                } else if(line.startsWith("Customer ID:")) {
                    id = line.substring(11).trim();
                }

                if (line.trim().equals("----------------------")) {
                    if (name != null && billNumber != null && amount > 0 && userRegion.equals(region)) {
                        Customer customer = new Customer(id, name, billNumber, amount, meterCode, userRegion, email);
                        bills.add(customer);

//                        System.out.println("Name: " + name);
//                        System.out.println("Amount: " + amount);
//                        System.out.println("Bill Number: " + billNumber);
//                        System.out.println("Meter Code: " + meterCode);
//                        System.out.println("Region: " + userRegion);
//                        System.out.println("Customer Email: " + email);
//                        System.out.println("----------------------");
                    }

                    name = null;
                    amount = 0.0;
                    billNumber = null;
                    meterCode = null;
                    userRegion = null;
                    email = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bills;
    }

    public Set<String> loadRegions() {
        Set<String> regions = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("bills.txt"))) {
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




    public double getTotalCollected(String region) {
        double totalAmount = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader("bills.txt"))) {
            String line;
            String currentRegion = null;
            double tempAmount = 0.0; // Temporarily store the amount

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim to remove extra spaces

                if (line.startsWith("Amount: $")) {
                    // Store the amount temporarily
                    String amountStr = line.substring(9).trim();
                    try {
                        tempAmount = Double.parseDouble(amountStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid amount format in bills.txt");
                        tempAmount = 0.0; // Reset tempAmount in case of error
                    }
                } else if (line.startsWith("Region:")) {
                    // Process the region and add tempAmount if it matches
                    currentRegion = line.substring(7).trim();
                    if (currentRegion.equals(region)) {
                        totalAmount += tempAmount;
                    }
                    // Reset tempAmount after processing
                    tempAmount = 0.0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalAmount;
    }

    public void manageUsers() {
        System.out.println("Admin is managing users...");
    }
}
