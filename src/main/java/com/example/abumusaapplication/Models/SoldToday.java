package com.example.abumusaapplication.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SoldToday extends ClientsItem{
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty clientType;
    public SoldToday(int id,String name,String clientType,String mat, int bill, String date, double weight, double piecePrice, double amount, double paidAmount){
        super(mat,bill,date,weight,piecePrice,amount,paidAmount);
        this.id=new SimpleIntegerProperty(this,"id",id);
        this.name=new SimpleStringProperty(this,"name",name);
        this.clientType= new SimpleStringProperty(this,"clientType",clientType);
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
}
