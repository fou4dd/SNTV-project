/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sntv;

import static DB.DataBase.connect;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author fou
 */
public class MainMenuController implements Initializable {

    @FXML 
    private Accordion lignesList;
    
    private void addBusLine(String name){
        HBox layout = new HBox();
        
        Label label = new Label(name);
        JFXButton back = new JFXButton("Back");
        JFXButton reserver = new JFXButton("Reserver");
        
        layout.getChildren().addAll(label, back, reserver);
    }
    
    private void addNewTitledPane(String title, ObservableList<Bus> listBus){
                
        TitledPane titledPane = new TitledPane();
        titledPane.setText(title);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Connection connection = connect();
        ObservableList<String> lignes = FXCollections.observableArrayList();
        
        try {
            PreparedStatement ps = connection.prepareStatement("select * from Lignes");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lignes.add(new Lignes(rs.getString("nomLigne")).getNomLigne());
            }
        } catch (SQLException ex) {
            System.out.println("Exception getting DATA");
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception closing DB");
            }
        }
        
        //for(String ligne : lignes){
        //    lignesList.getPanes().add();
        //}
    }
        
}    
    

