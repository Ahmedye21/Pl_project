package Core.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OldCustomer extends Customer {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OldCustomer(String id, String name, String email, String password, String role, String region) {
        super(id, name, email, password, role, region);  // Pass all 6 parameters to the Customer constructor
    }

    @Override
    public void saveCustomerInfo(String fileName) {
        // Save "Old Customer" information to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Old Customer - ID: " + getId());
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

    public void handleBillPayment(String meterCode) {
        if (meterCode.isEmpty()) {
            System.out.println("Error: Meter Code is required.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bill_payments.txt", true))) {
            writer.write("Meter Code: " + meterCode);
            writer.newLine();
            writer.write("Payment Date: " + dtf.format(LocalDateTime.now()));
            writer.newLine();
            writer.write("Customer ID: " + getId());
            writer.newLine();
            writer.write("Customer Name: " + getName());
            writer.newLine();
            writer.write("Customer Email: " + getEmail());
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();

            saveCustomerInfo("old_customers_bills.txt");

            System.out.println("Bill payment processed successfully for meter code: " + meterCode);
        } catch (IOException e) {
            System.out.println("Error: Failed to save bill payment information.");
            e.printStackTrace();
        }
    }
}
