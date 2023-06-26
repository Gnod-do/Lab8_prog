module Client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires common;



    opens Client to javafx.fxml;
    opens Client.controllers;



    exports Client;
    exports Client.controllers;
}