package Core.Controller;

import Core.Models.User;
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

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class SignupController {

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

    public void SwitchToLoginScene(ActionEvent event) throws IOException {
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
        String enteredConfirmPassword = confirmPassword.getText().trim();
        String enteredRegion = region.getText().trim();

        // Validate fields
        if (enteredUsername.isEmpty() || enteredEmail.isEmpty() || enteredPassword.isEmpty() || enteredRegion.isEmpty()) {
            signupMessage.setText("Please fill in all fields.");
            return;
        }

        if (!enteredPassword.equals(enteredConfirmPassword)) {
            signupMessage.setText("Passwords do not match.");
            return;
        }

        User newUser = new User(
                UUID.randomUUID().toString(),
                enteredUsername,
                enteredEmail,
                enteredPassword,
                "User",
                enteredRegion
        );

        if (newUser.register()) {
            signupMessage.setText("User registered successfully!");
        } else {
            signupMessage.setText("Error saving user data.");
        }
    }
}
