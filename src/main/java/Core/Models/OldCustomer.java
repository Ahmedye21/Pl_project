package Core.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OldCustomer extends Customer {
    private int billNumber = 0;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OldCustomer(String id, String name, String email, String password, String role, String region) {
        super(id, name, email, password, role, region);
    }

    @Override
    public void handleBillPayment(String meterCode , int price ) {
        if (meterCode.isEmpty()) {
            System.out.println("Error: Meter Code is required.");
            return;
        }

        billNumber++;  // Increment the bill number

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bills.txt", true))) {
            writer.write("Name: " + getName());
            writer.newLine();
            writer.write("Amount: $" + price);
            writer.newLine();
            writer.write("Bill Number: " + billNumber);
            writer.newLine();
            writer.write("Meter Code: " + meterCode);
            writer.newLine();
            writer.write("Region: " + getRegion());
            writer.newLine();
            writer.write("Payment Date: " + dtf.format(LocalDateTime.now()));
            writer.newLine();
            writer.write("Customer ID: " + getId());
            writer.newLine();
            writer.write("Customer Email: " + getEmail());
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();

            saveCustomerInfo("bills.txt");  // Save customer info as well

            System.out.println("Bill payment processed successfully for meter code: " + meterCode);
        } catch (IOException e) {
            System.out.println("Error: Failed to save bill payment information.");
            e.printStackTrace();
        }
    }

}
