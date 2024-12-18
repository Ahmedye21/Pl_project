package Core.Controller;

import Core.Models.Admin; // Import the Admin class
import Core.Models.Customer;
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
import java.util.Date;


import java.io.IOException;
import java.util.Set;

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

        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        Set<Customer> customers = admin.loadUsers();
        for (Customer customer : customers) {
            billsList.getItems().add(customer);
        }
    }

    public void onChange(ActionEvent event) {
        System.out.println(region.getSelectionModel().getSelectedItem());
    }

    public void viewBills(ActionEvent event) {
        totalCollected.setVisible(false);
        billsList.setVisible(true);
    }

    public void viewTotalCollected(ActionEvent event) {
        billsList.setVisible(false);
        totalCollected.setVisible(true);
        totalCollected.setText(region.getSelectionModel().getSelectedItem());
        System.out.println(region.getSelectionModel().getSelectedItem());
    }

    public void viewStatistics(ActionEvent event) {
        admin.viewReports();
        System.out.println(region.getSelectionModel().getSelectedItem());
    }

    public void manageUsers(ActionEvent event) throws IOException {
        admin.manageUsers();

        Parent root = new FXMLLoader(getClass().getResource("/Models/Admin/manage_users.fxml")).load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Manage Users");
        stage.setScene(scene);
        stage.show();
    }
}
