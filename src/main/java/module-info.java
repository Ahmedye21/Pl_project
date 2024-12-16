module com.example.Core {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;

    opens Core to javafx.fxml;
    opens Core.Models to javafx.fxml;
    opens Core.Controller to javafx.fxml;



    exports Core;
    exports Core.Models;
    exports Core.Controller;
}