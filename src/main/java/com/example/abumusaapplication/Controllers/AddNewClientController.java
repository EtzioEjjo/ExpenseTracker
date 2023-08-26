package com.example.abumusaapplication.Controllers;

import com.example.abumusaapplication.Models.DatabaseDriver;
import com.example.abumusaapplication.Utility.ClientTypes;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddNewClientController implements Initializable {
    public TextField addClient_TextField;
    public Button addClient_Button;
    public ComboBox<String> accountType_ComboBox;
    private String[] clientTypes;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientTypes=Arrays.stream(ClientTypes.values()).map(e->e.toString()).toArray(size->new String[size]);

        /*INTITIALIZING COMBOBOX*/
        accountType_ComboBox.getItems().addAll(clientTypes);
        accountType_ComboBox.getSelectionModel().selectFirst();
        setComboBoxCellFactory();

        addClient_Button.setOnAction(e->{

            try {
                String selectedVal=accountType_ComboBox.getSelectionModel().getSelectedItem().strip();

                System.out.println(addClient_TextField.getText());
                System.out.println(selectedVal);
                if (!addClient_TextField.getText().isEmpty()&&!selectedVal.isEmpty()) {
                    boolean addedClientFlag;

                    addedClientFlag = DatabaseDriver.addClient(addClient_TextField.getText().strip(),selectedVal);

                    if (addedClientFlag) {
                        Stage stage;
                        stage = (Stage) (addClient_Button.getScene().getWindow());
                        stage.close();


                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("لقد قمت بإضافة عميل موجود مسبقا.");
                        alert.showAndWait();
                    }
                }
            } catch (Exception ex) {
                System.out.println("couldnt add client");
            }

        });
    }


    private void setComboBoxCellFactory(){
        accountType_ComboBox.setCellFactory(listviewOfStrings->{
            return new ListCell<String>(){

                @Override
                protected void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(item==null||empty){
                        setText(null);
                    }else{
                        setText(item);
                        setAlignment(Pos.TOP_RIGHT);
                        setStyle("-fx-border-width:1px; -fx-border-color:#e0e0e0; -fx-font-size:1.2em; -fx-alignment:center-right;");



                    }                }
            };
        });
    }

}
