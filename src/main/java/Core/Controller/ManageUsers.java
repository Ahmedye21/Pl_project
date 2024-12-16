package Core.Controller;

import Core.Models.Customer;
import javafx.collections.ObservableList;
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
import javafx.collections.FXCollections;

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

    private ObservableList<Customer> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTable.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Customer clickedUser = row.getItem();
                    System.out.println("Double-clicked on: " + clickedUser.name + ", Role: " + clickedUser.getRole());
                    showEditDialog(clickedUser);
                }
            });

            return row;
        });

        // Populate initial data
        userList.addAll(
                new Customer(1,"Amos", "kero@gmail.com"),
                new Customer(2,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(3,"Amos", "kero@gmail.com"),
                new Customer(4,"Amos", "kero@gmail.com"),
                new Customer(5,"Amos", "kero@gmail.com")
        );
        userTable.setItems(userList);
    }

    @FXML
    private void handleAddUser() {
        Customer newUser = showAddDialog();
        if (newUser != null) {
            userList.add(newUser);
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
            userList.remove(user);  // Remove the user from the list
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
                user.name = usernameField.getText();
                user.role = roleField.getText();
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
