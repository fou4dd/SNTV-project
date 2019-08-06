/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sntv;

import static DB.DataBase.connect;
import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
    
    @FXML
    private AnchorPane timeRec;
    
    @FXML private Label clock;
    
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
    
        /*
            LOAD FiLE DATA
        */
        
        private void loadFile(String fileName){
            FileReader fr = null;
        try {
            fr = new FileReader(new File("src/resources/"+fileName+".txt"));
        } catch (FileNotFoundException ex) {
            System.err.println("could not load the file");
            ex.printStackTrace();
        }
        
        BufferedReader reader = new BufferedReader(fr);
        String line;
        
        try{
            line = reader.readLine();
            while(!line.equals("END")){
                if(line.startsWith("b ")){
                String[] currentLine = line.split(" / ");
                Bus bus = new Bus(currentLine[1], currentLine[2], currentLine[3], Integer.parseInt(currentLine[4]));
                Bus.buses.add(bus);
                }
                else if(line.startsWith("l ")){
                    String[] currentLine = line.split(" / ");
                    Lignes ligne = new Lignes(currentLine[1], currentLine[2], currentLine[3], Float.parseFloat(currentLine[4]));
                    Lignes.lignes.add(ligne);
                }
                else if(line.startsWith("c ")){
                    String[] currentLine = line.split(" / ");
                    Chauffeur chauffeur = new Chauffeur(currentLine[1], currentLine[2], currentLine[3], currentLine[4], currentLine[5]);
                    Chauffeur.chauffeur.add(chauffeur);
                }
                line = reader.readLine();
            }
     
        }catch(Exception e){
            e.printStackTrace();
        }
            
        }
        
        //LOAD BUSES TO DB
        private void loadBusesToDB() throws SQLException{
            Connection connection = connect();
            
            String query = "INSERT INTO Bus (nomLigne, Marque, Matricule, Capacite)"
                    + "VALUES (?, ?, ?, ?)";
            
            String query2 = "INSERT INTO Lignes (nomLigne, `Sntv Depart`, `SNTV Arrive`, prix)"
                    + "VALUES (?, ?, ?, ?)";
            
            String query3 = "INSERT INTO Choffeure (name, prenom, Nationalite, Adr, tel)"
                    + "VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
        try {
            
            ps = connection.prepareStatement(query);
            ps2 = connection.prepareStatement(query2);
            ps3 = connection.prepareStatement(query3);
            
            for(Bus bus : Bus.buses){
                ps.setString(1, bus.getNomLigne());
                ps.setString(2, bus.getMarque());
                ps.setString(3, bus.getMatricule());
                ps.setInt(4, bus.getCapacite());
                ps.addBatch();   // THE INSERT HAPPENS HERE
            }
            ps.executeBatch();
            
            
            for(Lignes ligne : Lignes.lignes){
                ps2.setString(1, ligne.getNomLigne());
                ps2.setString(2, ligne.getDepart());
                ps2.setString(3, ligne.getArrive());
                ps2.setFloat(4, ligne.getPrix());
                ps2.addBatch();   // THE INSERT HAPPENS HERE
            }
            ps2.executeBatch();
            
            for(Chauffeur chauffeur : Chauffeur.chauffeur){
                ps3.setString(1, chauffeur.getNom());
                ps3.setString(2, chauffeur.getPrenom());
                ps3.setString(3, chauffeur.getNat());
                ps3.setString(4, chauffeur.getAdr());
                ps3.setString(5, chauffeur.getTel());
                ps3.addBatch();   // THE INSERT HAPPENS HERE
            }
            ps3.executeBatch();
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("ERROR HERE");
            throw ex;
        }finally{
            ps.close();
            ps2.close();
            ps3.close();
            
        }
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
        
        //CLOC AND DATE
        final DateFormat format = DateFormat.getInstance();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), 
            new EventHandler() 
            {

            @Override
            public void handle(Event event) {
                     final Calendar cal = Calendar.getInstance();
                     clock.setText(format.format(cal.getTime()));
            }
            }));
   
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
            
            
            //LOADNG FILE
            loadFile("BUS");
        try {
            loadBusesToDB();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Could not load buses to DB");
        }
            /*
            System.out.println(Bus.buses.toString());
            System.out.println(Lignes.lignes.toString());
            System.out.println(Chauffeur.chauffeur.toString());
            */
    }
        
}    
    

