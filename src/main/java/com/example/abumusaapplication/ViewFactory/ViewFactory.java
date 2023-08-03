package com.example.abumusaapplication.ViewFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewFactory {



    public void addNewClientWindow(){
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Fxml/AddNewClient.fxml"));
        createStage(loader,"إضافة عميل جديد");



    }
    public void createStage(FXMLLoader loader,String title){
            Scene scene=null;
        try {
            scene= new Scene(loader.load());
            Stage stage= new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.showAndWait();
        }catch (Exception e){
            System.out.println("couldnt load add new client Window "+e.getMessage());

        }





    }
}
