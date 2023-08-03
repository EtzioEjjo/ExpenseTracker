package com.example.abumusaapplication.Controllers;

import com.example.abumusaapplication.Models.BalanceInquiryItem;
import com.example.abumusaapplication.Models.DatabaseDriver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class BalanceInquiryController implements Initializable {
    public TableView<BalanceInquiryItem> table_TableView;
    public TableColumn<BalanceInquiryItem,Integer> idCol;
    public TableColumn<BalanceInquiryItem,String> nameCol;
    public TableColumn<BalanceInquiryItem,Double> amountCol;
    public TableColumn<BalanceInquiryItem,Double> paid_amount_col;
    public TableColumn<BalanceInquiryItem,Double> fundsCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        if (!getData().isEmpty()){
        table_TableView.getItems().addAll(getData());
        }else {
            System.out.println("empty");
        }
    }

    private ObservableList<BalanceInquiryItem>getData(){
        return DatabaseDriver.getBalanceInquiryItems();

    }
    private void setCellValueFactories(){
        idCol.setCellValueFactory(cellData->cellData.getValue().idProperty().asObject());
        nameCol.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        amountCol.setCellValueFactory(cellData->cellData.getValue().amountProperty().asObject());
        paid_amount_col.setCellValueFactory(cellData->cellData.getValue().paid_amountProperty().asObject());
        fundsCol.setCellValueFactory(cellData->{
            DoubleProperty funds= new SimpleDoubleProperty();
            funds.set(cellData.getValue().getAmount()-cellData.getValue().getPaid_amount());
            return funds.asObject();});

    }
}
