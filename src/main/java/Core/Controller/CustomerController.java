package Core.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerController {

    @FXML private TextField metercode;
    @FXML private TextArea complaint;
    @FXML private Button paybill;
    @FXML private Button read_meter_code;
    @FXML private Button submit_complaint;
    @FXML private TextField fullname;
    @FXML private TextField address;
    @FXML private Button choose_file;
    @FXML private Button submitapp;

    private File selectedFile; // Holds the file chosen by the user
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Handle "Pay Bill" button to save bill payment details.
     */
    @FXML
    protected void handlePayBill(ActionEvent event) {
        String meterCode = metercode.getText();

        if (meterCode.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Meter Code is required.");
            return;
        }

        try (FileWriter writer = new FileWriter("bills.txt", true)) {
            String record = "Paid Bill - Meter Code: " + meterCode + ", Date: " + dtf.format(LocalDateTime.now()) + "\n";
            writer.write(record);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Bill payment saved successfully.");
            metercode.clear();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save bill payment.");
        }
    }

    /**
     * Handle "Submit Reading" button to save meter reading details.
     */
    @FXML
    protected void handleSubmitReading(ActionEvent event) {
        String meterCode = metercode.getText();

        if (meterCode.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Meter Code is required.");
            return;
        }

        try (FileWriter writer = new FileWriter("readings.txt", true)) {
            String record = "Meter Reading Submitted - Meter Code: " + meterCode + ", Date: " + dtf.format(LocalDateTime.now()) + "\n";
            writer.write(record);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Meter reading submitted successfully.");
            metercode.clear();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save meter reading.");
        }
    }

    /**
     * Handle "Submit Complaint" button to save a customer's complaint.
     */
    @FXML
    protected void handleSubmitComplaint(ActionEvent event) {
        String meterCode = metercode.getText();
        String complaintText = complaint.getText();

        if (meterCode.isEmpty() || complaintText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Meter Code and Complaint are required.");
            return;
        }

        String fileName = "Complaint_" + meterCode + "_" + System.currentTimeMillis() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Meter Code: " + meterCode + "\n");
            writer.write("Date: " + dtf.format(LocalDateTime.now()) + "\n");
            writer.write("Complaint: " + complaintText + "\n");
            showAlert(Alert.AlertType.INFORMATION, "Success", "Complaint saved successfully.");
            metercode.clear();
            complaint.clear();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save complaint.");
        }
    }

    /**
     * Handle "Choose File" button to allow users to pick a contract file.
     */
    @FXML
    protected void handleChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Contract File");

        // Optional: set file extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        selectedFile = fileChooser.showOpenDialog(choose_file.getScene().getWindow());

        if (selectedFile != null) {
            showAlert(Alert.AlertType.INFORMATION, "File Selected", "Selected File: " + selectedFile.getName());
        } else {
            showAlert(Alert.AlertType.WARNING, "No File Selected", "Please select a valid file.");
        }
    }

    /**
     * Handle "Submit Application" button to save new customer details into a file.
     */
    @FXML
    protected void handleSubmitApplication(ActionEvent event) {
        String fullName = fullname.getText();
        String addr = address.getText();

        if (fullName.isEmpty() || addr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Full Name and Address are required.");
            return;
        }

        if (selectedFile == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please upload the contract file.");
            return;
        }

        try (FileWriter writer = new FileWriter("new_customers.txt", true)) {
            String record = "New Customer Application:\n"
                    + "Full Name: " + fullName + "\n"
                    + "Address: " + addr + "\n"
                    + "Contract File: " + selectedFile.getAbsolutePath() + "\n"
                    + "Date: " + dtf.format(LocalDateTime.now()) + "\n\n";

            writer.write(record);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Application submitted successfully.");

            // Clear fields after submission
            fullname.clear();
            address.clear();
            selectedFile = null;
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save customer application.");
        }
    }

    /**
     * Display alert messages.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
