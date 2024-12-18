package Core.Controller;

import Core.Models.User;
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

        // Validate credentials
        User loggedInUser = validateCredentials(enteredUsername, enteredPassword);

        if (loggedInUser != null) {
            User.setLoggedInUser(loggedInUser); // Set the static logged-in user

            System.out.println("Login successful! Welcome " + loggedInUser.getName());

            // Redirect based on user role
            switch (loggedInUser.getRole()) {
                case "Admin":
                    loadScene(event, "/Models/Admin/admin_control_panel.fxml");
                    break;
                case "Customer":
                case "User":
                case "old":
                case "new":


                    loadScene(event, "/Models/Customer/Customer.fxml");
                    break;
                default:
                    wronglogin.setText("Unrecognized role.");
                    break;
            }
        } else {
            wronglogin.setText("Invalid username or password.");
        }
    }

    private User validateCredentials(String username, String password) {
        File file = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String id = null, name = null, email = null, filePassword = null, role = null, region = null, customerType = "New"; // Default customerType

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID:")) {
                    id = line.substring(3).trim();
                } else if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Email:")) {
                    email = line.substring(6).trim();
                } else if (line.startsWith("Password:")) {
                    filePassword = line.substring(9).trim();
                } else if (line.startsWith("Role:")) {
                    role = line.substring(5).trim();
                } else if (line.startsWith("Region:")) {
                    region = line.substring(7).trim();
                } else if (line.startsWith("CustomerType:")) {
                    customerType = line.substring(13).trim(); // Get customerType from file
                }

                // Check if we have all the necessary details
                if (id != null && name != null && email != null && filePassword != null && role != null && region != null) {
                    // Verify the username and password match
                    if (name.equals(username) && filePassword.equals(password)) {
                        return new User(id, name, email, filePassword, role, region, customerType); // Pass customerType correctly
                    }

                    // Reset fields for the next user in the file
                    id = name = email = filePassword = role = region = customerType = null;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading user file", e);
        }
        return null; // Return null if no match found
    }

    private void loadScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
