package com.example.abumusaapplication.Models;

import com.example.abumusaapplication.ViewFactory.ViewFactory;

public class Model {

    private  static Model instance;
    private  ViewFactory viewFactory;
    private Model(){
        this.viewFactory= new ViewFactory();
    }
    public static Model getInstance(){
    if (instance==null){
        instance= new Model();}
    return instance;
    }
    public ViewFactory getViewFactory(){

        return viewFactory;

    }

}
