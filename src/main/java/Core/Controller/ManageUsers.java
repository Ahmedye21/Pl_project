package Core.Controller;

import Core.Models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManageUsers {
    @FXML
    private TableView<Customer> userTable;

    @FXML
    private TableColumn<Customer, String> customerId;

    @FXML
    private TableColumn<Customer, String> userName;

    @FXML
    private TableColumn<Customer, String> userEmail;

    @FXML
    private TableColumn<Customer, String> userRole;

    @FXML
    public void initialize() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTable.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Customer clickedUser = row.getItem();
                    System.out.println("Double-clicked on: " + clickedUser.getName() + ", Role: " + clickedUser.getRole());
                    showEditDialog(clickedUser);
                }
            });

            return row;
        });

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            String id = null;
            String name = null;
            String email = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Email:")) {
                    email = line.substring(6).trim();
                } else if (line.startsWith("Role:")) {
                    id = String.valueOf(userTable.getItems().size() + 1);
                    if (name != null && email != null) {
                        userTable.getItems().add(new Customer(id, name, email));
                    }
                    name = null;
                    email = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddUser() {
        Customer newUser = showAddDialog();
        if (newUser != null) {
            // TODO: Add newUser to users.txt
            userTable.getItems().add(newUser);
        }
    }

    private void showEditDialog(Customer user) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit User");

        TextField usernameField = new TextField(user.getName());
        TextField roleField = new TextField(user.getRole());

        // Create the "Delete" button
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        // Handle the delete button action
        deleteButton.setOnAction(e -> {
            // TODO: Remove user from users.txt
            userTable.getItems().remove(user);
            dialog.close();  // Close the dialog
        });

        dialog.getDialogPane().setContent(new VBox(10,
                new Label("Username:"), usernameField,
                new Label("Role:"), roleField,
                deleteButton  // Add the delete button to the dialog
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                user.setName(usernameField.getText());
                user.setRole(roleField.getText());
            }
            return null;
        });

        dialog.showAndWait();
    }


    private Customer showAddDialog() {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Add User");

        TextField usernameField = new TextField();
        TextField roleField = new TextField();

        dialog.getDialogPane().setContent(new VBox(10,
                new Label("Username:"), usernameField,
                new Label("Role:"), roleField
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Customer(
                        1,
                        usernameField.getText(),
                        roleField.getText()
                );
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    public void back(ActionEvent event) throws IOException {
        Parent root =  new FXMLLoader(getClass().getResource("/Models/Admin/admin_control_panel.fxml")).load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Control Panel");
        stage.setScene(scene);
        stage.show();
    }
}
