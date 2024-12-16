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
import java.net.URL;
import java.util.Objects;

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

    public void SwitchToscene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Models/Customer/Customer.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToscene2(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/Models/User/signup.fxml");
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

        User user = new User().searchUser(enteredUsername, enteredPassword);

        if (user != null) {
            user.login();
            SwitchToscene1(event);
        } else {
            wronglogin.setText("Invalid username or password.");
        }
    }
}
