package Core.Controller;

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

import java.io.IOException;
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

    @FXML
    public void initialize() {
        region.getItems().addAll("Cairo", "Giza", "Helwan");
        region.getSelectionModel().select("Cairo");

        billsList.setVisible(false);
        totalCollected.setVisible(false);

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void onChange(ActionEvent event) {
        System.out.println(region.getSelectionModel().getSelectedItem());
    }

    public void viewBills(ActionEvent event) {
        totalCollected.setVisible(false);
        billsList.setVisible(true);
        billsList.getItems().add(new Customer(1,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(2,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(3,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(3,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(3,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(3,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(3,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(3,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(4,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(5,"Amos", "kero@gmail.com"));
        billsList.getItems().add(new Customer(6,"Amos", "kero@gmail.com"));
        System.out.println(region.getSelectionModel().getSelectedItem());
    }

    public void viewTotalCollected(ActionEvent event) {
        billsList.setVisible(false);
        totalCollected.setVisible(true);
        totalCollected.setText(region.getSelectionModel().getSelectedItem());
        System.out.println(region.getSelectionModel().getSelectedItem());
    }

    public void viewStatistics(ActionEvent event) {
//        billsList.getItems().add("meen el 5wl ely " + region.getSelectionModel().getSelectedItem());
        System.out.println(region.getSelectionModel().getSelectedItem());
    }

    public void manageUsers(ActionEvent event) throws IOException {
        Parent root =  new FXMLLoader(getClass().getResource("/Models/Admin/manage_users.fxml")).load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Manage Users");
        stage.setScene(scene);
        stage.show();
    }
}
