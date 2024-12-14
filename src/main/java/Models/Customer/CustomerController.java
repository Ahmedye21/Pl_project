package Models.Customer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerController {

    // FXML fields
    @FXML private TextField metercode;
    @FXML private TextArea complaint;
    @FXML private Button paybill;
    @FXML private Button read_meter_code;
    @FXML private Button submit_complaint;


    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


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


    @FXML
    protected void handleSubmitComplaint(ActionEvent event) {
        String meterCode = metercode.getText();
        String complaintText = complaint.getText();

        if (meterCode.isEmpty() || complaintText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Meter Code and Complaint are required.");
            return;
        }

        // Generate complaint file with unique name
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


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
