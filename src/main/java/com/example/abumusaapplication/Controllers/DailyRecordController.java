package com.example.abumusaapplication.Controllers;

import com.example.abumusaapplication.Models.DatabaseDriver;
import com.example.abumusaapplication.Models.SoldToday;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DailyRecordController implements Initializable {


    public TableView<SoldToday> table_TableView;
    public TableColumn<SoldToday,Integer> idCol;
    public TableColumn<SoldToday,String> nameCol;
    public TableColumn<SoldToday,Integer> billCol;
    public TableColumn<SoldToday,String> matCol;
    public TableColumn<SoldToday,String> dateCol;
    public TableColumn<SoldToday,Double> piecePriceCol;
    public TableColumn<SoldToday,Double> amountCol;
    public TableColumn<SoldToday,Double> paidAmountCol;
    public TableColumn<SoldToday,String> clientTypeCol;
    public TableColumn<SoldToday,Double> countCol;
    private ObservableList<SoldToday> tableData= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellFactories();
        getTodaysSales();
        if (!tableData.isEmpty()){
            table_TableView.getItems().addAll(tableData);}
    }



    private void getTodaysSales(){
        LocalDate lc=LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date= lc.format(formatter);
        tableData.addAll(DatabaseDriver.getTodaySales(date));

    }

    private void setCellFactories(){
        idCol.setCellValueFactory(dataCell->dataCell.getValue().idProperty().asObject());
        nameCol.setCellValueFactory(dataCell->dataCell.getValue().nameProperty());
        clientTypeCol.setCellValueFactory(dataCell->dataCell.getValue().clientTypeProperty());
        matCol.setCellValueFactory(e-> e.getValue().materialNameProperty());
        billCol.setCellValueFactory(e->e.getValue().billNumberProperty().asObject());
        piecePriceCol.setCellValueFactory(e-> e.getValue().piecePriceProperty().asObject());
        amountCol.setCellValueFactory(e-> e.getValue().amountProperty().asObject());
        countCol.setCellValueFactory(e->e.getValue().weight_NumberProperty().asObject());
        paidAmountCol.setCellValueFactory(e-> e.getValue().paidAmountProperty().asObject());
        dateCol.setCellValueFactory(e-> e.getValue().dateProperty());
    }
}
