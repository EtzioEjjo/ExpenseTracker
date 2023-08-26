package com.example.abumusaapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Fxml/IronWeights.fxml"));
//        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Fxml/PeopleClients.fxml"));
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Fxml/AddNewClient.fxml"));
//        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Fxml/SoldToday.fxml"));

//        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Fxml/BalanceInquiry.fxml"));
        Scene scene= new Scene(loader.load());
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
