package com.example.abumusaapplication.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DueExceeded extends BalanceInquiryItem{
    private StringProperty date;
    private StringProperty status;

    public DueExceeded(int id,String name,double funds,String date){
        super(id,name,funds);
        this.date =new SimpleStringProperty(this,"Date",date);
        this.status=new SimpleStringProperty(this,"Status",null);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty(){
        return date;


    }
    public void setDate(String date) {
        this.date.set(date);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
