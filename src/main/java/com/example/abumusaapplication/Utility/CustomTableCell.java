package com.example.abumusaapplication.Utility;

import com.example.abumusaapplication.Models.DueExceeded;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;

public class CustomTableCell extends TableCell<DueExceeded,String> {
    @Override
    public void updateItem(String item,boolean e){
    super.updateItem(item,e);
    if(e||item == null){
        setText(null);
    }else{
        setStyle("-fx-font-size:1em;-fx-font-weight:bold;");
        getTableRow().setPrefHeight(30);
        setAlignment(Pos.CENTER);
        setText(item);

        }
    }
}
