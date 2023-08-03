package com.example.abumusaapplication.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty clientType;

    private ObservableList<ClientsItem> clientsItems;

    public Client(int id,String name,String clientType){
        this.id=new SimpleIntegerProperty(this,"id",id);
        this.name=new SimpleStringProperty(this,"name",name);
        this.clientType= new SimpleStringProperty(this,"client type",clientType);
        clientsItems= FXCollections.observableArrayList();

    }
    public Client(int id,String name,String clientType,String mat, int bill, String date, double weight, double piecePrice, double amount, double paidAmount){
        this.id=new SimpleIntegerProperty(this,"id",id);
        this.name=new SimpleStringProperty(this,"name",name);
        this.clientType= new SimpleStringProperty(this,"client type",clientType);
        clientsItems= FXCollections.observableArrayList();
        ClientsItem clientsItem=new ClientsItem(mat,bill,date,weight,piecePrice,amount,paidAmount);
        clientsItems.add(clientsItem);
    }


    public ObservableList<ClientsItem> getClientsItems() {
        return clientsItems;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getClientType() {
        return clientType.get();
    }

    public StringProperty clientTypeProperty() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType.set(clientType);
    }

    @Override
    public String toString() {
        return this.clientType.get()+" , "+this.name.get();
    }
}
