package com.example.abumusaapplication.Controllers;

import com.example.abumusaapplication.Models.Client;
import com.example.abumusaapplication.Models.ClientsItem;
import com.example.abumusaapplication.Models.DatabaseDriver;
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


    public TableView<Object> table_TableView;
    public TableColumn<Object,Integer> idCol;
    public TableColumn<Object,String> nameCol;
    public TableColumn<Object,Integer> billCol;
    public TableColumn<Object,String> matCol;
    public TableColumn<Object,String> dateCol;
    public TableColumn<Object,Double> piecePriceCol;
    public TableColumn<Object,Double> amountCol;
    public TableColumn<Object,Double> paidAmountCol;
    public TableColumn<Object,String> clientTypeCol;
    public TableColumn<Object,Double> countCol;
    private ObservableList<Client> tableData= FXCollections.observableArrayList();
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
        idCol.setCellValueFactory(dataCell->((Client)dataCell.getValue()).idProperty().asObject());
        nameCol.setCellValueFactory(dataCell->((Client)dataCell.getValue()).nameProperty());
        clientTypeCol.setCellValueFactory(dataCell->((Client)dataCell.getValue()).clientTypeProperty());
        matCol.setCellValueFactory(e-> ((ClientsItem)e.getValue()).materialNameProperty());
        billCol.setCellValueFactory(e-> ((ClientsItem)e.getValue()).billNumberProperty().asObject());
        piecePriceCol.setCellValueFactory(e-> ((ClientsItem)e.getValue()).piecePriceProperty().asObject());
        amountCol.setCellValueFactory(e-> ((ClientsItem)e.getValue()).amountProperty().asObject());
        countCol.setCellValueFactory(e-> ((ClientsItem)e.getValue()).weight_NumberProperty().asObject());
        paidAmountCol.setCellValueFactory(e-> ((ClientsItem)e.getValue()).paidAmountProperty().asObject());
        dateCol.setCellValueFactory(e-> ((ClientsItem)e.getValue()).dateProperty());
    }
}
