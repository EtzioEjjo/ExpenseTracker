package com.example.abumusaapplication.Models;

import com.example.abumusaapplication.Utility.TablesNames;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.sql.*;
import java.util.Optional;

public class DatabaseDriver {
private static  String CONNECTION_STRING="jdbc:sqlite:C:/Users/Home/IdeaProjects/AbuMusaApplication/AbuMusa.db";
    /*CLIENT METHODS*/
    public static ObservableList<String> getClientsNames(){
        ObservableList<String> names=null;
        try(Connection conn= DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement GET_CLIENTS_NAMES= conn.prepareStatement("SELECT name FROM "+TablesNames.Clients.toString())){
            ResultSet resultSet=GET_CLIENTS_NAMES.executeQuery();
            names= FXCollections.observableArrayList();
            while (resultSet.next()){
                names.add(resultSet.getString("name"));

            }
            resultSet.close();
        }  catch (SQLException e){
            System.out.println("couldnt fetch clients names from database");
        }

        return names;
    }
    public static ObservableList<Client> getTodaySales(String date){
        ObservableList<Client> lst=FXCollections.observableArrayList();;
        try(Connection connection=DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement GET_TODAY_ITEMS= connection.prepareStatement("SELECT Clients.id,Clients.name,Clients.client_type,material,bill_number,date,weight_count,piece_price,amount,paid_amount FROM Clients INNER JOIN Client_Items ON Clients.id=Client_Items.id WHERE Client_Items.date =?")){
            GET_TODAY_ITEMS.setString(1,date);
            ResultSet resultSet=GET_TODAY_ITEMS.executeQuery();

            while (resultSet.next()){
                int clientId = resultSet.getInt("id");
                String clientName = resultSet.getString("name");
                String clientType = resultSet.getString("client_type");
                String material = resultSet.getString("material");
                int billNumber = resultSet.getInt("bill_number");
                String datee = resultSet.getString("date");
                double weightCount = resultSet.getDouble("weight_count");
                double piecePrice = resultSet.getDouble("piece_price");
                double amount = resultSet.getDouble("amount");
                double paidAmount = resultSet.getDouble("paid_amount");
                Client client;
//                item=new Client(r.getInt("id"),r.getString("name"),r.getString("client_type"),r.getString("material"),r.getInt("bill_number"),r.getString("date"),r.getDouble("weight_count"),r.getDouble("piece_price"),r.getDouble("amount"),r.getDouble("paid_amount"));
//                lst.add(item);
                Optional<Client> existingClient=lst.stream().filter(e->e.getId()==clientId).findFirst();

                if (existingClient.isPresent()){
                    client=existingClient.get();
                }else {
                    client= new Client(clientId,clientName,clientType);
                    lst.add(client);
                }
                ClientsItem clientsItem= new ClientsItem(material,billNumber,datee,weightCount,piecePrice,amount,paidAmount);
                client.getClientsItems().add(clientsItem);
            }
            resultSet.close();

        }catch (SQLException e){
            System.out.println("couldnt get todays sales "+e.getMessage());

        }
        System.out.println("today item size"+lst.size());
        return lst;

    }
    public static boolean addClient(String name,String clientType)throws Exception {
        System.out.println(name);
        boolean checkClient=false;
        try( Connection conn= DriverManager.getConnection("jdbc:sqlite:C:/Users/Home/IdeaProjects/AbuMusaApplication/AbuMusa.db");
             PreparedStatement CHECK_EXISTING_CLIENT=conn.prepareStatement("SELECT * FROM Clients WHERE name=?");
             PreparedStatement INSERT_INTO_CLIENT=conn.prepareStatement("INSERT INTO clients (name,client_type) VALUES(?,?)")){
            CHECK_EXISTING_CLIENT.setString(1,name);
            ResultSet resultSet =CHECK_EXISTING_CLIENT.executeQuery();
            if (resultSet.next()){
                System.out.println("client Exists");

            }else {
                INSERT_INTO_CLIENT.setString(1,name);
                INSERT_INTO_CLIENT.setString(2,clientType);
                INSERT_INTO_CLIENT.executeUpdate();

                checkClient=true;
                System.out.println("client added");
            }
            resultSet.close();

        }catch (SQLException e){
            System.out.println("couldnt preform database operation");

        }

        return checkClient;
    }
    public static int getClientId(String name) {
        int id=-1;
        try(Connection conn= DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement GET_CLIENT_ID=conn.prepareStatement("SELECT id FROM Clients WHERE name=?")){
            GET_CLIENT_ID.setString(1,name);
            ResultSet resultSet= GET_CLIENT_ID.executeQuery();
            if(resultSet.next()){
               id=resultSet.getInt("id");
                System.out.println(id);
            }
            resultSet.close();
        }catch (SQLException e){
            System.out.println("couldnt get the client id");


        }
        return id;
    }
    public static String getFirstOrLastClient(IntegerProperty firstOrLastClient){
        String firstOrLastName=null;
        if (firstOrLastClient.getValue()==0){

            try (Connection conn= DriverManager.getConnection(CONNECTION_STRING);
                 PreparedStatement FIRST_CLIENT= conn.prepareStatement("SELECT MIN(id),name FROM "+ TablesNames.Clients.toString())){
                ResultSet resultSet= FIRST_CLIENT.executeQuery();
                if (resultSet.next()){
                    firstOrLastName=resultSet.getString("name");
                }
                resultSet.close();

            }catch (SQLException e){
                System.out.println("couldnt get first client");

            }        } else if (firstOrLastClient.getValue()==1) {
            try (Connection conn= DriverManager.getConnection(CONNECTION_STRING);
                 PreparedStatement LAST_CLIENT= conn.prepareStatement("SELECT MAX(id),name FROM "+ TablesNames.Clients.toString())){
                ResultSet resultSet= LAST_CLIENT.executeQuery();
                if (resultSet.next()){
                    firstOrLastName=resultSet.getString("name");
                }
        }catch (Exception e ){
                System.out.println("couldnt get last client");}
        }
        return firstOrLastName;
    }

    public static ObservableSet<String> getAutoCompletionSet(){
ObservableSet<String> set=null;
        try (Connection conn=DriverManager.getConnection(CONNECTION_STRING);
        PreparedStatement GET_NAMES_SET=conn.prepareStatement("SELECT name FROM Clients")){

            ResultSet resultSet= GET_NAMES_SET.executeQuery();
            set= FXCollections.observableSet();
            while (resultSet.next()){

                set.add(resultSet.getString("name"));
            }
            resultSet.close();

        }catch (SQLException e){
            System.out.println("Couldnt get Auto Completion Name's Set ");
        }

        return set;
    }

    public static boolean addClientsItem(int id,String mat,int bill,String date,double weight,double piecePrice,double amount,double paidAmount){
        boolean addedNewItem=false;
        try(Connection conn=DriverManager.getConnection(CONNECTION_STRING);
        PreparedStatement ADD_CLIENT_ITEM=conn.prepareStatement("INSERT INTO Client_Items values(?,?,?,?,?,?,?,?)")){
            ADD_CLIENT_ITEM.setInt(1,id);
            ADD_CLIENT_ITEM.setString(2,mat);
            ADD_CLIENT_ITEM.setInt(3,bill);
            ADD_CLIENT_ITEM.setString(4,date);
            ADD_CLIENT_ITEM.setDouble(5,weight);
            ADD_CLIENT_ITEM.setDouble(6,piecePrice);
            ADD_CLIENT_ITEM.setDouble(7,amount);
            ADD_CLIENT_ITEM.setDouble(8,paidAmount);

            int rowsAffected=ADD_CLIENT_ITEM.executeUpdate();
            System.out.println(rowsAffected);
            if (rowsAffected>0){addedNewItem=true;}

        }catch (SQLException e){
            System.out.println("couldnt insert clientItem into the database "+e.getMessage());

        }

        return addedNewItem;
    }
    public static ObservableList<ClientsItem> clientsItemsList(int id){
        ObservableList<ClientsItem> list= FXCollections.observableArrayList();
        try(Connection conn=DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement GET_CLIENTS_ITEMS= conn.prepareStatement("SELECT * FROM Client_Items WHERE id=?") ){
            ClientsItem item=null;
            GET_CLIENTS_ITEMS.setInt(1,id);
            ResultSet r= GET_CLIENTS_ITEMS.executeQuery();
            while (r.next()){
                item= new ClientsItem(r.getString("material"),r.getInt("bill_number"),r.getString("date"),r.getDouble("weight_count"),r.getDouble("piece_price"),r.getDouble("amount"),r.getDouble("paid_amount"));
                list.add(item);
            }

            r.close();



        }catch (SQLException e){
            System.out.println("couldnt get clients items list");
        }
        return list;

    }
    public static void deleteClient(int id) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement DELETE_CLIENT = conn.prepareStatement("DELETE FROM Clients WHERE id=?");
             PreparedStatement DELETE_CLIENTS_ITEMS = conn.prepareStatement("DELETE FROM Client_Items WHERE id=?")) {

            DELETE_CLIENT.setInt(1, id);
            DELETE_CLIENTS_ITEMS.setInt(1, id);
            conn.setAutoCommit(false);

            int affectedRows = DELETE_CLIENT.executeUpdate();
            int affectedRows2 = DELETE_CLIENTS_ITEMS.executeUpdate();

            // Check if both operations were successful
            if (affectedRows > 0 && affectedRows2 > 0) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Could not remove the client " + e.getMessage());

            // Rollback in case of an error

        }
    }
    public static boolean deleteClientItem(ClientsItem item){

        System.out.println(item.getPaidAmount());
        try(Connection conn= DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement DELETE_CLIENT_ITEM=conn.prepareStatement("DELETE FROM Client_Items WHERE material=? AND bill_number=? AND date=? AND weight_count=? AND piece_price=? AND amount=? AND paid_amount=?")){
            DELETE_CLIENT_ITEM.setString(1,item.getMaterialName());
            DELETE_CLIENT_ITEM.setDouble(2,item.getBillNumber());
            DELETE_CLIENT_ITEM.setString(3, item.getDate());
            DELETE_CLIENT_ITEM.setDouble(4,item.getWeight_Number());
            DELETE_CLIENT_ITEM.setDouble(5,item.getPiecePrice());
            DELETE_CLIENT_ITEM.setDouble(6,item.getAmount());
            DELETE_CLIENT_ITEM.setDouble(7,item.getPaidAmount());
            int affectedRow=DELETE_CLIENT_ITEM.executeUpdate();
            if (affectedRow>0){
            System.out.println("row has been deleted successfully");
            return true;
            }
        }catch (SQLException e){
            System.out.println("couldnt delete the specifed row from the database "+e.getMessage());
        }


    return false;
    }


    /*BalanceInquiry  Methods*/
    public static ObservableList<BalanceInquiryItem> getBalanceInquiryItems(){
        ObservableList<BalanceInquiryItem> items=FXCollections.observableArrayList();
        BalanceInquiryItem item=null;
        try(Connection conn= DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement GET_INQUIRYiTEMS= conn.prepareStatement("SELECT * FROM "+TablesNames.Balance_Inquiry.toString())) {
            ResultSet resultSet = GET_INQUIRYiTEMS.executeQuery();
            while (resultSet.next()){
                item = new BalanceInquiryItem(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3), resultSet.getDouble(4));
                items.add(item);



            }
            resultSet.close();
        }catch (SQLException e){
            System.out.println("couldnt get items from "+TablesNames.Balance_Inquiry.toString());


        }
        return items;

    }


    /*DueExceeded Methods*/
    public static ObservableList<DueExceeded> getDueExceededItems(){
        DueExceeded item=null;
        ObservableList<DueExceeded> items=FXCollections.observableArrayList();
        try(Connection conn=DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement GET_DUEEXCEEDED_ITEMS=conn.prepareStatement("SELECT Clients.id,Clients.name,(Balance_Inquiry.amount_total-Balance_Inquiry.paid_amount_total) AS funds,Max(Client_Items.date) AS date FROM Clients INNER JOIN Client_Items ON Clients.id=Client_Items.id INNER JOIN Balance_Inquiry ON Clients.id=Balance_Inquiry.id GROUP BY Client_Items.id")){
            ResultSet r=GET_DUEEXCEEDED_ITEMS.executeQuery();
            while (r.next()){
                int id= r.getInt("id");
                String name=r.getString("name");
                double funds=r.getDouble("funds");
                String date=r.getString("date").strip();
                item=new DueExceeded(id,name,funds,date);
                items.add(item);
            }
            r.close();
              }catch (SQLException e){
            System.out.println("couldnt get DueExceeded data from database "+e.getMessage());
        }



        return items;
    }

    /*
    *
    * UTILITY METHODS
    * */
}
