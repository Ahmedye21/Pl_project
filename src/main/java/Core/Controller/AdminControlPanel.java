package Core.Controller;

import Core.Models.Admin;
import Core.Models.Customer;
import Core.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.Date;

public class AdminControlPanel {
    @FXML
    private ComboBox<String> region;

    @FXML
    private TableView<Customer> billsList;

    @FXML
    public TableColumn<Customer, Integer> customerId;

    @FXML
    public TableColumn<Customer, String> customerName;

    @FXML
    public TableColumn<Customer, String> customerEmail;

    @FXML
    public TableColumn<Customer, Double> amount;

    @FXML
    public TableColumn<Customer, Date> date;

    @FXML
    public Label totalCollected;

    private Admin admin;

    public AdminControlPanel() {
        admin = new Admin();
    }

    @FXML
    public void initialize() {
        Set<String> regions = admin.loadRegions();
        region.getItems().addAll(regions);

        if (!regions.isEmpty()) {
            region.getSelectionModel().select(0);
        }

        billsList.setVisible(false);
        totalCollected.setVisible(false);

        // Set up TableView columns
        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }



    public void onChange(ActionEvent event) {
        // Print the selected region for debugging purposes
        String selectedRegion = region.getSelectionModel().getSelectedItem();
        System.out.println("Selected Region: " + selectedRegion);
    }

    public void viewBills(ActionEvent event) {
        totalCollected.setVisible(false);
        billsList.setVisible(true);
        billsList.getItems().clear();

        // Load bills data and add to the TableView
        Set<Customer> bills = admin.loadBills(region.getSelectionModel().getSelectedItem());
        for (Customer customer : bills) {
//            System.out.println("dbg: " + customer.getName());
            billsList.getItems().add(customer);
        }
    }

    public void viewTotalCollected(ActionEvent event) {
        billsList.setVisible(false);
        totalCollected.setVisible(true);

        double total = admin.getTotalCollected(region.getSelectionModel().getSelectedItem());  // Get total collected amount from Admin
        totalCollected.setText("Total Collected: $" + total);  // Display the amount
        System.out.println("Total Collected: $" + total);
    }

    public void viewStatistics(ActionEvent event) {
        admin.viewReports(); // Show statistics/reports
        System.out.println("Statistics for region: " + region.getSelectionModel().getSelectedItem());
    }

    public void manageUsers(ActionEvent event) throws IOException {
        admin.manageUsers();  // Manage users functionality

        // Load new scene for managing users
        Parent root = new FXMLLoader(getClass().getResource("/Models/Admin/manage_users.fxml")).load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Manage Users");
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent event) {
        User loggedInUser = User.getLoggedInUser();
        if (loggedInUser != null) {
            loggedInUser.logout();
        }

        // Redirect to the login page
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Models/User/login.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
