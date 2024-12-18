package Core.Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewCustomer extends Customer {
    private File selectedFile;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public NewCustomer(String id, String name, String email, String role) {
        super(id, name, email, role);
    }

    @Override
    public void saveCustomerInfo(String fileName) {
        // Use the "New Customer" format
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("New Customer - ID: " + getId());
            writer.newLine();
            writer.write("Name: " + getName());
            writer.newLine();
            writer.write("Email: " + getEmail());
            writer.newLine();
            writer.write("Date: " + dtf.format(LocalDateTime.now()));
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleSubmitApplication(String fullName, String address, File contractFile) {
        if (fullName.isEmpty() || address.isEmpty()) {
            System.out.println("Error: Full Name and Address are required.");
            return;
        }

        if (contractFile == null) {
            System.out.println("Error: Contract file is required.");
            return;
        }

        this.selectedFile = contractFile;
        saveCustomerInfo("new_customers.txt");
        System.out.println("New customer application submitted successfully!");
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }
}
