package com.example.abumusaapplication.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class IronWeights {

    private static final ObservableMap<Double,Double> ironRadius_ironPrice= FXCollections.observableHashMap();
    private final DoubleProperty length;
    private final DoubleProperty count;
    private final DoubleProperty weight;



    public IronWeights(double length,double count){
        this.length=new SimpleDoubleProperty(this,"length",length);
        this.count=new SimpleDoubleProperty(this,"count",count);
        this.weight=new SimpleDoubleProperty(this,"weight",count);
    }



    private void setIronValues(){
        IronWeights.ironRadius_ironPrice.put(8.0,0.415);
        IronWeights.ironRadius_ironPrice.put(10.0,0.617);
        IronWeights.ironRadius_ironPrice.put(12.0,0.900);
        IronWeights.ironRadius_ironPrice.put(14.0,1.210);
        IronWeights.ironRadius_ironPrice.put(16.0,1.580);
        IronWeights.ironRadius_ironPrice.put(18.0,2.000);
        IronWeights.ironRadius_ironPrice.put(20.0,2.500);
        IronWeights.ironRadius_ironPrice.put(25.0,3.850);
        IronWeights.ironRadius_ironPrice.put(32.0,6.320);
    }

}
