package Models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignupController {

    private static final Logger LOGGER = Logger.getLogger(SignupController.class.getName());

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField region;

    @FXML
    private Button registerButton;

    @FXML
    private Button login;

    @FXML
    private Label signupMessage;


    public void SwitchToscene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Models/User/login.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRegister() {
        String enteredUsername = username.getText().trim();
        String enteredEmail = email.getText().trim();
        String enteredPassword = password.getText().trim();
        String enteredRegion = region.getText().trim();

        if (enteredUsername.isEmpty() || enteredEmail.isEmpty() || enteredPassword.isEmpty() || enteredRegion.isEmpty()) {
            signupMessage.setText("Please fill in all fields.");
            return;
        }

        if (!enteredPassword.equals(confirmPassword.getText())) {
            signupMessage.setText("Passwords do not match.");
            return;
        }

        // Save user data to file
        if (saveUserData(enteredUsername, enteredEmail, enteredPassword, enteredRegion)) {
            signupMessage.setText("User registered successfully!");
        } else {
            signupMessage.setText("Error saving user data.");
        }
    }

    private boolean saveUserData(String username, String email, String password, String region) {
        File file = new File("users.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writer.write("Name: " + username);
            writer.newLine();
            writer.write("Email: " + email);
            writer.newLine();
            writer.write("Password: " + password);
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
}
