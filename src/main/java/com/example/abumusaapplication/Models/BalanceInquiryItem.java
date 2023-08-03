package com.example.abumusaapplication.Models;

import javafx.beans.property.*;

public class BalanceInquiryItem {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty amount;
    private DoubleProperty paid_amount;
    private DoubleProperty funds;

    public BalanceInquiryItem(int id,String name,double funds){
        this.id= new SimpleIntegerProperty(this,"id",id);
        this.name=new SimpleStringProperty(this,"name",name);
        this.funds=new SimpleDoubleProperty(this,"funds",funds);
    }

    public BalanceInquiryItem(int id,String name,double amount,double paid_amount){
        this.id= new SimpleIntegerProperty(this,"id",id);
        this.name=new SimpleStringProperty(this,"name",name);
        this.amount= new SimpleDoubleProperty(this,"amount",amount);
        this.paid_amount= new SimpleDoubleProperty(this,"paid_amount",paid_amount);
        this.funds=new SimpleDoubleProperty(this,"funds",0);
        updateFunds();


    }

    private void updateFunds(){
        this.funds.set(this.amount.get()-this.paid_amount.get());

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

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public double getPaid_amount() {
        return paid_amount.get();
    }

    public DoubleProperty paid_amountProperty() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount.set(paid_amount);
    }

    public double getFunds() {
        return funds.get();
    }

    public DoubleProperty fundsProperty() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds.set(funds);
    }
}
