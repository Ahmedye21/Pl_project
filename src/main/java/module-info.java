module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;

    opens Models to javafx.fxml;
    opens Models.User to javafx.fxml;

    exports Models;
    exports Models.User;
}