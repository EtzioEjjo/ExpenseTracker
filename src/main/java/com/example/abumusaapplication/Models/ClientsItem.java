package com.example.abumusaapplication.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientsItem {
    private final SimpleStringProperty materialName;
    private final SimpleIntegerProperty billNumber;
    private final SimpleStringProperty date;
    private final SimpleDoubleProperty weight_Number;
    private final SimpleDoubleProperty piecePrice;
    private final SimpleDoubleProperty amount;
    private final SimpleDoubleProperty paidAmount;
    private final SimpleDoubleProperty funds;
    private  IntegerProperty col_num;

    public ClientsItem(String mat, int bill, String date, double weight, double piecePrice, double amount, double paidAmount,int colNumber){
        this.materialName= new SimpleStringProperty(this,"Material Name",mat);
        this.billNumber= new SimpleIntegerProperty(this,"Bill Number",bill);
        this.date= new SimpleStringProperty(this,"Date",date);
        this.weight_Number= new SimpleDoubleProperty(this,"Weight Number",weight);
        this.piecePrice= new SimpleDoubleProperty(this,"Piece Price",piecePrice);
        this.amount= new SimpleDoubleProperty(this,"Amount",amount);
        this.paidAmount= new SimpleDoubleProperty(this,"Paid Amount",paidAmount);
        this.funds= new SimpleDoubleProperty(this,"Funds",0);
        this.col_num= new SimpleIntegerProperty(this,"Column Number",colNumber);


    }
    public ClientsItem(String mat, int bill, String date, double weight, double piecePrice, double amount, double paidAmount){
        this(mat,bill,date,weight,piecePrice,amount,paidAmount,DatabaseDriver.getHighestColumnNumber());
    //updateFunds();
    }

    public int getCol_num() {
        return col_num.get();
    }

    public IntegerProperty col_numProperty() {
        return col_num;
    }

    public void setCol_num(int col_num) {
        this.col_num.set(col_num);
    }

    public String getMaterialName() {
        return materialName.get();
    }

    public SimpleStringProperty materialNameProperty() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName.set(materialName);
    }

    public Integer getBillNumber() {
        return billNumber.get();
    }

    public SimpleIntegerProperty billNumberProperty() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber.set(billNumber);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getWeight_Number() {
        return weight_Number.get();
    }

    public SimpleDoubleProperty weight_NumberProperty() {
        return weight_Number;
    }

    public void setWeight_Number(double weight_Number) {
        this.weight_Number.set(weight_Number);
    }

    public double getPiecePrice() {
        return piecePrice.get();
    }

    public SimpleDoubleProperty piecePriceProperty() {
        return piecePrice;
    }

    public void setPiecePrice(double piecePrice) {
        this.piecePrice.set(piecePrice);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public Double getPaidAmount() {
        return paidAmount.get();
    }

    public SimpleDoubleProperty paidAmountProperty() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount.set(paidAmount);
    }

    public double getFunds() {
        return funds.get();
    }

    public SimpleDoubleProperty fundsProperty() {
        return funds;
    }

    public void setFunds(double funds) {

        this.funds.set(funds);
    }
    private void updateFunds(){
        double amount=getAmount();
        double paidAmount=getPaidAmount();
        double total=amount-paidAmount;
        this.funds.set(total);

    }

}
