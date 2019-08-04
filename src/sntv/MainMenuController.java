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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author fou
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private AnchorPane accueil_page,bus_page,ligne_page,chauffeur_page,aide_page,contact_page;
    
    @FXML
    private JFXButton accueil_btn,bus_btn,ligne_btn,chauffeur_btn,aide_btn,contact_btn;

    @FXML 
    private Accordion lignesList;
    
    private void makeAllInvisible(){
        accueil_page.setVisible(false);
        bus_page.setVisible(false);
        ligne_page.setVisible(false);
        chauffeur_page.setVisible(false);
        aide_page.setVisible(false);
        contact_page.setVisible(false);
    }
    
    //FONCTION RETURNS ANCHORPANE WITH ID FOR EACH LIGNE
        private AnchorPane addAnchorPane(String ID){
            AnchorPane pane = new AnchorPane();
            pane.setId(ID);
            return pane;
        }
    
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
        ObservableList<Lignes> lignes = FXCollections.observableArrayList();
        
        try {
            PreparedStatement ps = connection.prepareStatement("select * from Lignes");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lignes.add(new Lignes(rs.getInt("NLigne"), rs.getString("nomLigne"), rs.getString("Sntv Depart"),
                        rs.getString("SNTV Arrive"), rs.getFloat("prix")));
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
        
        accueil_page.setVisible(true);
        bus_page.setVisible(false);
        ligne_page.setVisible(false);
        chauffeur_page.setVisible(false);
        aide_page.setVisible(false);
        contact_page.setVisible(false);
        
        accueil_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                makeAllInvisible();
                accueil_page.setVisible(true);
            } 
        });
        
        
        bus_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                makeAllInvisible();
                bus_page.setVisible(true);
            } 
        });
        
        ligne_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                makeAllInvisible();
                ligne_page.setVisible(true);
            } 
        });
        
        chauffeur_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                makeAllInvisible();
                chauffeur_page.setVisible(true);
            } 
        });
        
        aide_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                makeAllInvisible();
                aide_page.setVisible(true);
            } 
        });
        
        contact_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                makeAllInvisible();
                contact_page.setVisible(true);
            } 
        });
        
        //AFFICHER LES LIGNES DANS LA LIST DES LIGNES COMME TITLEDPANE 
        
        for(Lignes ligne : lignes){
            lignesList.getPanes().add(new TitledPane(ligne.getNomLigne(), addAnchorPane(ligne.getNomLigne())));
        }
        
    }
        
}    
    

