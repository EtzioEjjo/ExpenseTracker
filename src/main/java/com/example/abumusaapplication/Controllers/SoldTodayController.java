package com.example.abumusaapplication.Controllers;

import com.example.abumusaapplication.Models.Client;
import com.example.abumusaapplication.Models.ClientsItem;
import com.example.abumusaapplication.Models.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SoldTodayController implements Initializable {
    public TreeView<Object> treeview_TreeView;
    private ObservableList<Client> data;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData();
        TreeItem<Object> root= new TreeItem<>();
        setCellFactories();
        if (!data.isEmpty()) {
            data.forEach(client -> {
                TreeItem<Object> clientTreeItem = new TreeItem<>(client.toString());

                for (ClientsItem item : client.getClientsItems()) {
                    TreeItem<Object> itemTreeItem = new TreeItem<>(item);
                    clientTreeItem.getChildren().add(itemTreeItem);
                }

                root.getChildren().add(clientTreeItem);
            });
        }

        treeview_TreeView.setRoot(root);
        treeview_TreeView.setShowRoot(false);
        treeview_TreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }



    private void getData(){
        LocalDate lc=LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date= lc.format(formatter);
        data= FXCollections.observableArrayList(DatabaseDriver.getTodaySales(date));
    }

    private void setCellFactories(){
        treeview_TreeView.setStyle("-fx-color:#000;");
        treeview_TreeView.setCellFactory(p->{
            return new TreeCell<>(){
                @Override
                protected void updateItem(Object obj, boolean empty) {
                    super.updateItem(obj, empty);
                    if (empty){
                        setText(null);
                    }else {
//                        setHeight(100);
                        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                        setPadding(new Insets(2, 0, 2, 0));
                        setStyle("-fx-font-size:1.5em;");
                        setGraphic(null);
                        String s=" ".repeat(20);

                        setText(obj.toString());
                         if(obj instanceof ClientsItem){
                             ClientsItem item=(ClientsItem) obj;
                            setText(item.getMaterialName()+s+item.getBillNumber()+s+item.getDate()+s+item.getWeight_Number()+s+item.getPiecePrice()+s+item.getAmount()+s+item.getPaidAmount());
                        }
                    }                           }
            };


        });



    }
}
