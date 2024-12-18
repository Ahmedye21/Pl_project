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
    @FXML private TextField price;
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
        System.out.println("Current logged-in user: " + (User.getLoggedInUser() != null ? User.getLoggedInUser().getName() : "None"));

        String meterCode = metercode.getText();

        // Check if meter code is empty
        if (meterCode.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Meter Code is required.");
            return;
        }

        String priceText = price.getText();

        if (priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Price is required.");
            return;
        }

        int priceValue = 0;
        try {
            priceValue = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid price format.");
            return;
        }

        User loggedInUser = User.getLoggedInUser();
        if (loggedInUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is logged in.");
            return;
        }

        if ("old".equals(loggedInUser.getRole())) {
            loggedInUser = new OldCustomer(loggedInUser.getId(), loggedInUser.getName(), loggedInUser.getEmail(),
                    loggedInUser.getPassword(), loggedInUser.getRole(), loggedInUser.getRegion());

            ((OldCustomer) loggedInUser).handleBillPayment(meterCode, priceValue);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Bill payment submitted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "The logged-in user does not have the correct role.");
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