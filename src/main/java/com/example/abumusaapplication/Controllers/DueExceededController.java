package com.example.abumusaapplication.Controllers;

import com.example.abumusaapplication.Models.DatabaseDriver;
import com.example.abumusaapplication.Models.DueExceeded;
import com.example.abumusaapplication.Utility.CustomTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DueExceededController implements Initializable {
    public TableColumn<DueExceeded,Integer> id_Col;
    public TableColumn<DueExceeded,String> name_Col;
    public TableColumn<DueExceeded,Double> funds_Col;
    public TableColumn<DueExceeded, String> lastPaymentDate_Col;
    public TableColumn<DueExceeded,String> afterMonthDate_Col;
    public TableColumn<DueExceeded,String> clientStatus_Col;
    public Button print_Button;
    public Button exist_Button;
    public TableView<DueExceeded> table_TableView;
    private ObservableList<DueExceeded> data= FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     steCellValueFactories();
     setCellFactories();
     getData();
     if (!data.isEmpty()){
         table_TableView.getItems().addAll(data);
     }

    }




    private void getData(){
        data.addAll(DatabaseDriver.getDueExceededItems());
    }
    private void steCellValueFactories(){
        id_Col.setCellValueFactory(dataCell-> dataCell.getValue().idProperty().asObject());
        name_Col.setCellValueFactory(dataCell->dataCell.getValue().nameProperty());
        funds_Col.setCellValueFactory(dataCell->dataCell.getValue().fundsProperty().asObject());
        lastPaymentDate_Col.setCellValueFactory(dataCell->dataCell.getValue().dateProperty());
        afterMonthDate_Col.setCellValueFactory(dataCell->{
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String lastPaymentDate=dataCell.getValue().getDate();
            LocalDate currentMonth=LocalDate.parse(lastPaymentDate,formatter);
            LocalDate addExtraMonth=currentMonth.plusMonths(1);
            LocalDate now=LocalDate.now();
            if (now.isAfter(addExtraMonth)){
                dataCell.getValue().setStatus("متأخر");
            }else{

                dataCell.getValue().setStatus("غير متأخر");
            }
            return  new SimpleStringProperty(addExtraMonth.toString());


        });

        clientStatus_Col.setCellValueFactory(dataCell-> dataCell.getValue().statusProperty());

    }

    public void setCellFactories(){
        id_Col.setCellFactory(colum->new TableCell<DueExceeded,Integer>() {
            @Override
            public void updateItem(Integer item,boolean e){
                super.updateItem(item,e);
                if(e||item == null){
                    setText(null);
                }else{
                    setStyle("-fx-font-size:1em;-fx-font-weight:bold;");
                    getTableRow().setPrefHeight(30);
                    setAlignment(Pos.CENTER);
                    setText(item.toString());

                }
            }


        });
        funds_Col.setCellFactory(colum->new TableCell<DueExceeded,Double>() {
            @Override
            public void updateItem(Double item,boolean e){
                super.updateItem(item,e);
                if(e||item == null){
                    setText(null);
                }else{
                    setStyle("-fx-font-size:1em;-fx-font-weight:bold;");
                    getTableRow().setPrefHeight(30);
                    setAlignment(Pos.CENTER);
                    setText(item.toString());

                }
            }


        });

        name_Col.setCellFactory(col->new CustomTableCell());
        lastPaymentDate_Col.setCellFactory(col->new CustomTableCell());
        afterMonthDate_Col.setCellFactory(col->new CustomTableCell());
        clientStatus_Col.setCellFactory(col->new CustomTableCell());
    }}


