package Core.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label wronglogin;
    @FXML
    private Button userlogin;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    public void SwitchToscene2(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/Models/User/signup.fxml");
        System.out.println(resource);
        Parent root = FXMLLoader.load(Objects.requireNonNull(resource));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogin(ActionEvent event) throws IOException {
        String enteredUsername = username.getText().trim();
        String enteredPassword = password.getText().trim();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            wronglogin.setText("Please enter both username and password.");
            return;
        }

        String userRole = validateCredentials(enteredUsername, enteredPassword);

        if (userRole != null) {
            System.out.println("Login successful! Role: " + userRole);

            if (userRole.equals("Admin")) {
                loadScene(event, "/Models/Admin/admin_control_panel.fxml");
            } else if (userRole.equals("Customer") || userRole.equals("User")) {
                loadScene(event, "/Models/Customer/Customer.fxml");
            } else {
                wronglogin.setText("Unrecognized role.");
            }
        } else {
            wronglogin.setText("Invalid username or password.");
        }
    }

    private String validateCredentials(String username, String password) {
        File file = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String storedUsername = null;
            String storedPassword = null;
            String storedRole = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    storedUsername = line.substring(5).trim();
                }
                if (line.startsWith("Password:")) {
                    storedPassword = line.substring(9).trim();
                }
                if (line.startsWith("Role:")) {
                    storedRole = line.substring(5).trim();
                }

                if (storedUsername != null && storedPassword != null && storedRole != null) {
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return storedRole;
                    }
                    storedUsername = null;
                    storedPassword = null;
                    storedRole = null;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to login", e);
        }
        return null;
    }

    private void loadScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
