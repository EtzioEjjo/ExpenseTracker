package com.example.abumusaapplication.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class IronWeightsController implements Initializable {
    public TableView table_TableView;
    public TableColumn radiusCol;
    public TableColumn lengthCol;
    public TableColumn countCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    table_TableView.setEditable(true);
    }
}
