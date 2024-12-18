package Core.Controller;

import Core.Models.NewCustomer;
import Core.Models.OldCustomer;
import Core.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CustomerController {

    @FXML private TextField metercode;
    @FXML private TextArea complaint;
    @FXML private Button paybill;
    @FXML private Button submit_complaint;
    @FXML private TextField fullname;
    @FXML private TextField address;
    @FXML private Button choose_file;
    @FXML private Button submitapp;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private File selectedFile;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

        NewCustomer newCustomer = new NewCustomer("newId123", fullName, "newemail@example.com", "old");
        newCustomer.handleSubmitApplication(fullName, addr, selectedFile);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Application submitted successfully.");
    }



    @FXML
    protected void handlePayBill(ActionEvent event) {
        // Log the current logged-in user to the console for debugging
        System.out.println("Current logged in user: " + (User.getLoggedInUser() != null ? User.getLoggedInUser().getName() : "None"));

        // Get the meter code from the input field
        String meterCode = metercode.getText();

        // Check if the meter code field is empty and show an alert if it is
        if (meterCode.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Meter Code is required.");
            return;
        }

        // Get the logged-in user
        User loggedInUser = User.getLoggedInUser();

        // If no user is logged in, show an error alert
        if (loggedInUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is logged in.");
            return;
        }

        // Check if the logged-in user is an instance of OldCustomer
        if (loggedInUser instanceof OldCustomer loggedInCustomer) {
            // Process the bill payment for the old customer
            loggedInCustomer.handleBillPayment(meterCode);

            // Show a success alert after the bill payment is processed
            showAlert(Alert.AlertType.INFORMATION, "Success", "Bill payment submitted successfully.");
        } else {
            // If the logged-in user is not an OldCustomer, show an error alert
            showAlert(Alert.AlertType.ERROR, "Error", "The logged-in user is not an OldCustomer.");
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

        String fileName = "Complaint_" + meterCode + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Meter Code: " + meterCode);
            writer.newLine();
            writer.write("Date: " + dtf.format(LocalDateTime.now()));
            writer.newLine();
            writer.write("Complaint: " + complaintText);
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Complaint saved successfully.");
            metercode.clear();
            complaint.clear();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save complaint.");
        }
    }



    @FXML
    protected void handleChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        // Call logout method from User class
        User loggedInUser = User.getLoggedInUser();
        if (loggedInUser != null) {
            loggedInUser.logout();
        }

        // Redirect to the login page
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Models/User/login.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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