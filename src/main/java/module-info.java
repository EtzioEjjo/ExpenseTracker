module com.example.abumusaapplication{

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires org.controlsfx.controls;
    requires vaadin.autocompletetextfield;


    opens com.example.abumusaapplication to javafx.fxml;
    exports com.example.abumusaapplication;
    exports com.example.abumusaapplication.Models;
    exports com.example.abumusaapplication.Controllers;
    exports com.example.abumusaapplication.ViewFactory;
    exports com.example.abumusaapplication.Utility;
}