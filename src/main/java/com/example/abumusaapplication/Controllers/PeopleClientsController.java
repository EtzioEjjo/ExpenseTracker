package com.example.abumusaapplication.Controllers;

import com.example.abumusaapplication.Models.ClientsItem;
import com.example.abumusaapplication.Utility.ConfirmationDialogue;
import com.example.abumusaapplication.Models.DatabaseDriver;
import com.example.abumusaapplication.Models.Model;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class PeopleClientsController implements Initializable {

    public Button addNewClient_Button;
    public Button addNewItem_Button;
    public TextField paidAmount_TextField;
    public TextField amount_TextField;
    public TextField piecePrice_Button;
    public TextField number_weight_TextField;
    public TextField materialName_TextField;
    public TextField billNumber_TextField;
    public Label client_id;
    public TextField client_name;
    public TableColumn<ClientsItem,String> name_Col;
    public TableColumn<ClientsItem,Integer> billNum_Col;
    public TableColumn<ClientsItem,String> date_Col;
    public TableColumn<ClientsItem,Double> weight_Col;
    public TableColumn<ClientsItem,Double> price_Col;
    public TableColumn<ClientsItem,Double> amount_Col;
    public TableColumn<ClientsItem,Double> paidAmount_Col;
    public TableColumn<ClientsItem,Double> funds_Col;
    public TableView<ClientsItem> table_TableView;
    public Button deleteClient_Button;
    public Label amountSum_Label;
    public Label paidAmountSum_Label;
    public Label funds_Label;
    public Button deleteClientItemRow_Button;
    public Button nextClient_Button;
    public Button prevClient_Button;
    public Button firstClient_Button;
    public Button lastClient_Button;
    public Button print_Button;
    public Button todaysSales_Button;
    ObservableList<ClientsItem> tableData= FXCollections.observableArrayList();
    ObservableList<String>clientsNamesForPrevnNextButton=FXCollections.observableArrayList();
    IntegerProperty firstOrLastClient;

    private ObservableSet<String> names= FXCollections.observableSet(DatabaseDriver.getAutoCompletionSet());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttonsTraversal();
        defaultValues();
        setTableCellFactory();
        TextFields.bindAutoCompletion(client_name,names);
        getClientsNamesForPrevNext();

        deleteClientItemRow_Button.setOnAction(e->{handleDeletionButton(null,e);});
        tableData.addListener((ListChangeListener<? super ClientsItem>) change->{
//            amountSum_Label.textProperty().unbind();
            if (!tableData.isEmpty()) {
                amountSum_Label.textProperty().bind(Bindings.createStringBinding(() -> {
                    double sum = tableData.stream()
                            .mapToDouble(item -> item.getAmount())
                            .sum();
                    return Double.toString(sum);
                }, tableData));

                paidAmountSum_Label.textProperty().bind(Bindings.createStringBinding(() -> {
                    double sum = tableData.stream().mapToDouble(item -> item.getPaidAmount()).sum();

                    return Double.toString(sum);

                }, tableData));

                funds_Label.textProperty().bind(Bindings.createStringBinding(() -> {
                  double sum=0;
                  double funds=0;
                    for (int i=0;i<tableData.size();i++){
                      sum=tableData.get(i).getAmount()-tableData.get(i).getPaidAmount();
                      funds+=sum;
                  }
                    return Double.toString(funds);

                }, tableData));
            }


    });
        client_id.setText("");
        client_name.textProperty().addListener((o,oldVal,newVal)->{
             int id= DatabaseDriver.getClientId(client_name.getText().strip());
            client_id.setText(Integer.toString(id));
            tableData.clear();
            tableData.addAll(DatabaseDriver.clientsItemsList(Integer.parseInt(client_id.getText())));
            //tableData=DatabaseDriver.clientsItemsList(Integer.parseInt(client_id.getText()));
//            System.out.println(tableData);
          table_TableView.setItems(tableData);
        });

        addNewItem_Button.setOnAction(e->{
            if(client_name.getText().isEmpty() ||client_name.getText().isBlank()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("الرجاء إدخال اسم العميل");
                alert.showAndWait();

            }else{
                int id= Integer.parseInt(client_id.getText().strip());
                String mat=materialName_TextField.getText().strip().isEmpty()?"----":materialName_TextField.getText().strip();
                int bill=billNumber_TextField.getText().strip().isEmpty()?0:Integer.parseInt(billNumber_TextField.getText().strip());
                double weight=number_weight_TextField.getText().strip().isEmpty()?0.0:Double.parseDouble(number_weight_TextField.getText().strip());
                double piecePrice=piecePrice_Button.getText().strip().isEmpty()?0.0:Double.parseDouble(piecePrice_Button.getText().strip());
                double amount=amount_TextField.getText().strip().isEmpty()?0.0:Double.parseDouble(amount_TextField.getText().strip());
                double paidAmount=paidAmount_TextField.getText().strip().isEmpty()?0.0:Double.parseDouble(paidAmount_TextField.getText().strip());
                LocalDate localDate= LocalDate.now();
                DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
                localDate.format(formatter);
                addNewItem(id,mat,bill,localDate.toString(),weight,piecePrice,amount,paidAmount);
                ClientsItem clientsItem= new ClientsItem(mat,bill,localDate.toString(),weight,piecePrice,amount,paidAmount);
                tableData.add(clientsItem);
        }});

        number_weight_TextField.textProperty().addListener(e->{updateSum();});

        piecePrice_Button.textProperty().addListener(e->{updateSum();});

        deleteClient_Button.setOnAction(e->{
            ConfirmationDialogue dialogue= new ConfirmationDialogue();
            Optional<ButtonType> result=dialogue.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    System.out.println("am in");
                    DatabaseDriver.deleteClient(getClientID());
                    String name=client_name.getText().strip();
                    names.remove(name);
                    clientsNamesForPrevnNextButton.remove(name);
                    TextFields.bindAutoCompletion(client_name, names);
                    client_name.setText("");
                    client_id.setText("");
                    table_TableView.getItems().clear();
                }
            }});

        table_TableView.setOnKeyPressed(keyEvent->{handleDeletionButton(keyEvent,null);});
        firstClient_Button.setOnAction(e->{
            firstOrLastClient= new SimpleIntegerProperty(0);
            String name=DatabaseDriver.getFirstOrLastClient(firstOrLastClient);
            if (name!=null){
            client_name.setText(name);}

        });
        lastClient_Button.setOnAction(e->{
            firstOrLastClient= new SimpleIntegerProperty(1);
            String name=DatabaseDriver.getFirstOrLastClient(firstOrLastClient);
            if (name!=null){
                client_name.setText(name);}
        });

        nextClient_Button.setOnAction(e->{updateNextButton();});
        prevClient_Button.setOnAction(e->updatePrev());




    }




    //1 for next and 0 for prev
    private void updateNextButton(){
        int index ,next,size;
        if (!clientsNamesForPrevnNextButton.isEmpty()) {
            size=clientsNamesForPrevnNextButton.size();
            System.out.println(size);
            if(client_name.getText().isBlank()||client_name.getText().isEmpty()){
                client_name.setText(clientsNamesForPrevnNextButton.get(0));

            }else{
                index=clientsNamesForPrevnNextButton.indexOf(client_name.getText().strip());
                System.out.println(index);
                next=index+1;
                if (next>size-1){
                    next=0;
                }
                client_name.setText(clientsNamesForPrevnNextButton.get(next));
            }}

    }

    private void updatePrev(){
        int index,prev,size;
        if (!clientsNamesForPrevnNextButton.isEmpty()) {
            size=clientsNamesForPrevnNextButton.size();
            if (client_name.getText().isBlank()||client_name.getText().isEmpty()){
                client_name.setText(clientsNamesForPrevnNextButton.get(size-1));

            }else{
                index=clientsNamesForPrevnNextButton.indexOf(client_name.getText().strip());
                prev=index-1;
                if (prev<0){
                    prev=size-1;
                }
                client_name.setText(clientsNamesForPrevnNextButton.get(prev));
            }


        }


    }

    private void getClientsNamesForPrevNext(){

        clientsNamesForPrevnNextButton.addAll(DatabaseDriver.getClientsNames());

    }

//    private String getLastRowOfFunds(TableView tableView){
//        if (!tableView.getItems().isEmpty()) {
//            int lastIndex = tableView.getItems().size();
//            ClientsItem clientsItem = (ClientsItem) tableView.getItems().get(lastIndex);
//            return Double.toString(clientsItem.getFunds());
//        }
//
//    return null;
//    }
//    private String calculatePaidAmountSum(TableView tableView){
//        ObservableList<ClientsItem > data =tableView.getItems();
//        double sum=0;
//        for (ClientsItem item:data){
//            sum+=item.getPaidAmount();
//        }
//        return Double.toString(sum);
//
//    }
//
//    private String calculateAmountSum(TableView tableView){
//        ObservableList<ClientsItem> data= tableView.getItems();
//        double sum=0;
//        for (ClientsItem item:data){
//            sum+= item.getAmount();
//
//        }
//        return Double.toString(sum);
//
//
//    }

    private void handleDeletionButton(KeyEvent keyEvent, ActionEvent action){
        ClientsItem clientItem=table_TableView.getSelectionModel().getSelectedItem();
        if (keyEvent!=null){
        if(keyEvent.getCode()== KeyCode.DELETE){
        deleteItemRow(clientItem);}
        } else if (action!=null) {
            deleteItemRow(clientItem);
        }
    }
    private void deleteItemRow(ClientsItem clientItem){
        if(clientItem!=null){
            boolean isRowDeletedFromDatabase=DatabaseDriver.deleteClientItem(clientItem);
            if (isRowDeletedFromDatabase){
                tableData.remove(clientItem);
                table_TableView.refresh();}
        }

    }

    private void setTableCellFactory(){

        name_Col.setCellValueFactory(clientsItemStringCellDataFeatures -> clientsItemStringCellDataFeatures.getValue().materialNameProperty());
        billNum_Col.setCellValueFactory(f-> f.getValue().billNumberProperty().asObject());
        date_Col.setCellValueFactory(f-> f.getValue().dateProperty());
        weight_Col.setCellValueFactory(f-> f.getValue().weight_NumberProperty().asObject());
        price_Col.setCellValueFactory(f-> f.getValue().piecePriceProperty().asObject());
        amount_Col.setCellValueFactory(f-> f.getValue().amountProperty().asObject());
        paidAmount_Col.setCellValueFactory(f->f.getValue().paidAmountProperty().asObject());
        funds_Col.setCellValueFactory(f->{
            ClientsItem clientsItem=f.getValue();
            int rowIndex=f.getTableView().getItems().indexOf(clientsItem);

            if(rowIndex==0){
                f.getValue().setFunds(f.getValue().getAmount()-f.getValue().getPaidAmount());
                return f.getValue().fundsProperty().asObject();
            }else{
                ClientsItem previous= f.getTableView().getItems().get(rowIndex-1);
                f.getValue().setFunds(previous.getFunds()+f.getValue().getAmount()-f.getValue().getPaidAmount());
                return f.getValue().fundsProperty().asObject();
            }

        });

    }
    private int getClientID(){
        int id=Integer.parseInt(client_id.getText());
        return id;


    }
    private void updateSum(){
        try {
            double nw = Double.parseDouble(number_weight_TextField.getText());
            double pp = Double.parseDouble(piecePrice_Button.getText());
            double res = nw * pp;
            amount_TextField.setText(Double.toString(res));
        }catch (NumberFormatException e){
            System.out.println("not a number,please enter a number");
        }

    }    public void defaultValues(){
        amount_TextField.setText("0.0");
        paidAmount_TextField.setText("0.0");
        billNumber_TextField.setText("0");
        number_weight_TextField.setText("0.0");
        piecePrice_Button.setText("0.0");


    }


    private void addNewItem(int id,String mat,int bill,String date,double weight,double piecePrice,double amount,double paidAmount){

        boolean addedItem=DatabaseDriver.addClientsItem(id,mat,bill,date,weight,piecePrice,amount,paidAmount);
        if (addedItem){
            materialName_TextField.clear();
            billNumber_TextField.clear();
            number_weight_TextField.clear();
            piecePrice_Button.clear();
            amount_TextField.clear();
            paidAmount_TextField.clear();
        }

    }



    private void buttonsTraversal(){
        client_name.setOnKeyPressed(e->{
            if (e.getCode().toString().equals("TAB")){
            materialName_TextField.requestFocus();}
        });
        addNewClient_Button.setOnAction(e->{
            Model.getInstance().getViewFactory().addNewClientWindow();
            clientsNamesForPrevnNextButton.clear();
          getClientsNamesForPrevNext();
            client_name.setText("");
        });

        materialName_TextField.requestFocus();

        materialName_TextField.setOnKeyPressed(e->{
            if (e.getCode().toString().equals("TAB")){
                billNumber_TextField.requestFocus();
                e.consume();

            }
        });
        addNewItem_Button.setOnAction(e->{materialName_TextField.requestFocus();});
        addNewItem_Button.setOnKeyPressed(e->{
            if (e.getCode().toString().equals("TAB")){
                materialName_TextField.requestFocus();
                e.consume();
            }
        });
        billNumber_TextField.setOnKeyPressed(e->{if(e.getCode().toString().equals("TAB"))
        {number_weight_TextField.requestFocus();
            e.consume();}});
        number_weight_TextField.setOnKeyPressed(e->{
            if (e.getCode().toString().equals("TAB")){
                piecePrice_Button.requestFocus();
                e.consume();

            }
        });
        piecePrice_Button.setOnKeyPressed(e->{
            if (e.getCode().toString().equals("TAB")){
                amount_TextField.requestFocus();
                e.consume();

            }
        });
        amount_TextField.setOnKeyPressed(e->{
            if (e.getCode().toString().equals("TAB")){
                paidAmount_TextField.requestFocus();
                e.consume();

            }        });

        paidAmount_TextField.setOnKeyPressed(e->{
            if (e.getCode().toString().equals("TAB")){
                addNewItem_Button.requestFocus();
                e.consume();
            }
        });


    }}
