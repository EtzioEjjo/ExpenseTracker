package com.example.abumusaapplication.Utility;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ConfirmationDialogue extends Dialog {

    public ConfirmationDialogue() {
        super();
        this.setTitle("حذف العميل");
        buildUI();

    }


    private void buildUI() {
        Pane pane= createGridPane();
        getDialogPane().setContent(pane);
        ButtonType okButton=new ButtonType("نعم", ButtonType.OK.getButtonData());
        ButtonType cancelButton=new ButtonType("لا", ButtonType.CANCEL.getButtonData());
        getDialogPane().getButtonTypes().addAll(okButton,cancelButton);
        setResultConverter(buttontype->{
            if(buttontype==okButton){return ButtonType.OK; }
            else {return  false;}
        });

    }


    private Pane createGridPane(){
        VBox content= new VBox(10);
        content.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        content.setPrefWidth(400);
        content.setPrefHeight(50);
        GridPane gridPane= new GridPane();
        Label confirm= new Label("هل ترغب بالاستمرار ؟");
        confirm.setFont(Font.font(15));

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(3,1,1,3));

        gridPane.add(confirm,0,0);
        content.getChildren().add(gridPane);
        return content;
    }

}