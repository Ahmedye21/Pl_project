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

import java.io.*;
import java.util.UUID;

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
    private TableColumn<Customer, String> userRegion;

    @FXML
    public void initialize() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        userRegion.setCellValueFactory(new PropertyValueFactory<>("region"));

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

        // Read users from the file and populate the table
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            String id = null, name = null, email = null, password = null, role = null, region = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID:")) {
                    id = line.substring(4).trim();
                } else if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Email:")) {
                    email = line.substring(6).trim();
                } else if (line.startsWith("Password:")) {
                    password = line.substring(9).trim();
                } else if (line.startsWith("Role:")) {
                    role = line.substring(5).trim();
                } else if (line.startsWith("Region:")) {
                    region = line.substring(7).trim();
                } else if (line.startsWith("----------------------")) {
                    if (id != null && name != null && email != null && role != null && password != null && region != null) {
                        userTable.getItems().add(new Customer(id, name, email, password, role, region));
                    }
                    id = name = email = password = role = region = null;
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
            updateUsersFile(); // Update the file after adding a new user
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
            // Delete user from the TableView and users.txt
            userTable.getItems().remove(user);
            updateUsersFile();  // Update the file after deletion
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
                updateUsersFile();  // Update the file after editing
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
        TextField regionField = new TextField();

        dialog.getDialogPane().setContent(new VBox(10,
                new Label("Username:"), usernameField,
                new Label("Role:"), roleField,
                new Label("Region:"), regionField
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Customer(
                        UUID.randomUUID().toString(),
                        usernameField.getText(),
                        "",
                        "",
                        roleField.getText(),
                        regionField.getText()
                );
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    // Method to update the users.txt file after any change
    private void updateUsersFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (Customer user : userTable.getItems()) {
                writer.write("ID: " + user.getId() + "\n");
                writer.write("Name: " + user.getName() + "\n");
                writer.write("Email: " + user.getEmail() + "\n");
                writer.write("Password: " + user.getPassword() + "\n");
                writer.write("Role: " + user.getRole() + "\n");
                writer.write("Region: " + user.getRegion() + "\n");
                writer.write("----------------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
