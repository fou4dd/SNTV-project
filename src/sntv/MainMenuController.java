/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sntv;

import static DB.DataBase.connect;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author fou
 */
public class MainMenuController implements Initializable {
    private static Map<String, VBox> vboxes = new HashMap<String, VBox>();
    public static Map<String, JFXButton> buttons = new HashMap<String, JFXButton>();
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private AnchorPane accueil_page,bus_page,ligne_page,chauffeur_page,aide_page,contact_page, reservation_page;
    
    @FXML
    private JFXButton accueil_btn,bus_btn,ligne_btn,chauffeur_btn,aide_btn,contact_btn
                        , reserveBtn;
    
    @FXML private JFXTextField name, lastName, idNumber;

    @FXML 
    private Accordion lignesList;
    
    @FXML private ImageView exit; 
    
    @FXML private Label clock;
    
    @FXML private JFXListView listOfBuses;
    @FXML private JFXListView listOfLignes;
    @FXML private JFXListView listOfChauffeur;
    
    @FXML private TableView<Bus> busTableView;
    @FXML private TableColumn<Bus, String> nomLigne;
    @FXML private TableColumn<Bus, String> marque;
    @FXML private TableColumn<Bus, String> matricule;
    @FXML private TableColumn<Bus, Integer> capacite;
    
    @FXML private TableView<Lignes> lignesTable;
    @FXML private TableColumn<Lignes, String> ligneName;
    @FXML private TableColumn<Lignes, String> depart;
    @FXML private TableColumn<Lignes, String> arrive;
    @FXML private TableColumn<Lignes, Float> prix;
    
    @FXML private TableView<Chauffeur> chauffeurTable;
    @FXML private TableColumn<Chauffeur, String> nom;
    @FXML private TableColumn<Chauffeur, String> prenom;
    @FXML private TableColumn<Chauffeur, String> nat;
    @FXML private TableColumn<Chauffeur, String> adr;
    @FXML private TableColumn<Chauffeur, String> tel;
    
    private boolean HaveIRun = false;

    private void loadProgramFileOf(Lignes ligne) throws FileNotFoundException{
        FileReader fr = null;
        try {
            fr = new FileReader(new File("src/resources/programmeDesBuses/"+ligne.getNomLigne()+".txt"));
        } catch (FileNotFoundException ex) {
            System.err.println("could not load the file");
            ex.printStackTrace();
        }
        
        BufferedReader reader = new BufferedReader(fr);
        String line;
        int timeBetween = 0;
        
        try{
            line = reader.readLine();
            if(line.startsWith("tb ")){
                String[] timeB = line.split(" / ");
                timeBetween = Integer.parseInt(timeB[1]);
            }
            while(!line.endsWith("END")){
                if(line.startsWith("s ")){
                    String[] currentLine = line.split(" / ");
                    Schedule schedule = new Schedule(currentLine[1], Integer.parseInt(currentLine[2]), currentLine[3], currentLine[4]);
                    schedule.setTimeBetween(timeBetween);
                    Schedule.schedule.add(schedule);
                }
                line = reader.readLine();
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    //FOR EVERY LIGNE THE FUNCTON SHOWS THE BUSES OF THE LIGNE AS BUTTONS ON THE HBOX
    private void showBusesOfeveryLigne(){
        for(Map.Entry<String, VBox> map : vboxes.entrySet()){
            for(Bus bus : Bus.buses){
                if(map.getKey().equals(bus.getNomLigne())){
                    //get the button from button hashmap
                    buttonsDesign(buttons.get(bus.getMatricule()));
                    map.getValue().getChildren().add(buttons.get(bus.getMatricule()));
                }
            }
        }
    }
    
    //FUNCTON THAT STORES HBOX 
    private void storeVboxes(Lignes ligne){
        VBox vb = new VBox();
        vb.setId(ligne.getNomLigne());
        vboxes.put(ligne.getNomLigne(), vb);
    }
    
     //FUNCTON THAT STORES BUTTONS 
    private void storeButtons(Bus bus){
        JFXButton button = new JFXButton(bus.getMarque());
        button.setId(bus.getMatricule());
        buttons.put(bus.getMatricule(), button);
    }
    
    public static void updateButtons(Bus bus, JFXButton button){
        for(Map.Entry<String, JFXButton> map : buttons.entrySet()){
            //remove the old button and add the new one
            if(map.getValue().equals(bus)){
                buttons.remove(bus.getMatricule());
                
            }
        }
    }
    
    //FUNCTON THAT RETURNS LIST OF BUSES OF A GIVEN LIGNE 
    private ObservableList<Bus> getListOfBusOf(Lignes ligne){
        ObservableList<Bus> listOfBuses = FXCollections.observableArrayList();
        for(Bus bus : Bus.buses){
                if(bus.getNomLigne().equals(ligne.getNomLigne())){
                    listOfBuses.add(bus);
                }
        }
        return listOfBuses;
    }
    
    //GO THROUGH EVERY LIGNE AND GET EVERY LIGNE BUSES USING THE FUCTION ABOVE
    private void getEveryLigneBuses(){
        for(Lignes ligne : Lignes.lignes){
            ligne.setListDesBus(getListOfBusOf(ligne));
        }
    }
    
    private void makeAllInvisible(){
        accueil_page.setVisible(false);
        bus_page.setVisible(false);
        ligne_page.setVisible(false);
        chauffeur_page.setVisible(false);
        aide_page.setVisible(false);
        contact_page.setVisible(false);
        reservation_page.setVisible(false);
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
                    Lignes ligne = new Lignes(currentLine[1], currentLine[2], currentLine[3],
                            Float.parseFloat(currentLine[4]));
                    Lignes.lignes.add(ligne);
                }
                else if(line.startsWith("c ")){
                    String[] currentLine = line.split(" / ");
                    Chauffeur chauffeur = new Chauffeur(currentLine[1], currentLine[2], currentLine[3],
                            currentLine[4], currentLine[5]);
                    Chauffeur.chauffeur.add(chauffeur);
                }
                line = reader.readLine();
            }
     
        }catch(Exception e){
            e.printStackTrace();
        }
            
        }
        
        //LOAD BUSES TO DB
        private void loadDataToDB() throws SQLException{
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
            System.out.println("ERROR HERE");
            throw ex;
        }finally{
            ps.close();
            ps2.close();
            ps3.close();
        }
        }
        
        //BUTTONS DESIGN FUNCTION
        private void buttonsDesign(JFXButton button){
            button.getStyleClass().add("bus_buttons");
            button.getStylesheets().add("resources/menuStyle.css");
            button.setPrefWidth(858);
            button.setPrefHeight(100);     
            button.setDisable(true);
            
        }
        
        private void reserve() throws Exception{
             for(Map.Entry<String, JFXButton> map : buttons.entrySet()){
                map.getValue().setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                    reservation_page.setVisible(true);
                    accueil_page.setVisible(false);
                
                
                
             
                reserveBtn.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("clicked");
                            for(Bus bus : Bus.buses){
                                if(map.getValue().getId() == bus.getMatricule()){
                                Reservation reservation = new Reservation();
                                bus.getReservations().add(reservation);
                                bus.setReservations(bus.getReservations());
                                if(bus.getCapacite() == bus.getReservations().size()){
                                    bus.setFull(true);
                                    //map.getValue().setDisable(true);
                                    reservation_page.setVisible(false);
                                    accueil_page.setVisible(true);
                                    System.out.println("is full : " + bus.isFull());
                                    }
                                
                            
  
                            //take the info from ui and create a passenger
                            String firstName = name.getText().trim();
                            String last = lastName.getText().trim();
                            String idNum = idNumber.getText().trim();
                            
                            name.setText("");
                            lastName.setText("");
                            idNumber.setText("");
                            
                            if(firstName.isEmpty() || last.isEmpty() || idNum.isEmpty()){
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Format error");
                                String content = "Empty fields error please enter some informatiom in the fields";
                                alert.setContentText(content);
                                alert.showAndWait();
                                System.err.println("Fields empty !");
                            }else{
                                Passenger passenger = new Passenger(firstName, last, idNum);
                                passenger.passenger.add(passenger);
                                
                                for(Lignes ligne : Lignes.lignes){
                                    if(ligne.getNomLigne().equals(bus.getNomLigne())){   
                                        Voyage voyage = new Voyage(bus, ligne, java.time.LocalDate.now().toString());
                                        Voyage.voyages.add(voyage);
                                        Reservation res = new Reservation(voyage, passenger);       
                                        Reservation.reservation.add(res);
                                        
                                        try {
                                            loadPVtoDataBase(voyage, passenger);
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        }
                                       
                                    }
                                 
                                }
                     
                            }
                            
                        }
                            }
                        }
                    });
                    } 
                });
             }
        }
        
        private void loadPVtoDataBase(Voyage voyage, Passenger passenger) throws SQLException{
            Connection connection = connect();
            
            String query1 = "INSERT INTO Passager(NPiceDidentite, nom, prenom) "
                    + "VALUES('"+passenger.getIdNumber()+"',"
                    + " '"+passenger.getFirstName()+"',"
                    + " '"+passenger.getLastName()+"')";
            
            String query2 = "INSERT INTO Voyage(NPassager, NBus, NLigne, DateDepart) "
                    + "VALUES((SELECT NPassager FROM Passager WHERE NPiceDidentite = '"+passenger.getIdNumber()+"'), (SELECT NBus from Bus WHERE Matricule = '"+voyage.getBus().getMatricule()+"'),"
                    + "(SELECT NLigne from Lignes WHERE nomLigne = '"+voyage.getLigne().getNomLigne()+"') ,"
                    + " '"+voyage.getStartDate()+"')";
            
            PreparedStatement ps1 = connection.prepareStatement(query1);        
            PreparedStatement ps2 = connection.prepareStatement(query2);
            
            ps1.execute();
            ps2.execute();
            
            
            ps2.close();
            ps1.close();        
        }

        private boolean returnHaveIRunValue() throws FileNotFoundException{
             File file = new File("state_track.txt");
             Scanner scnr = new Scanner(file);
             boolean flag = false;
            //Reading each line of file using Scanner class
            int lineNumber = 1;
            while(scnr.hasNextLine()){
                String line = scnr.nextLine();
                if(line.equals("true")){
                    flag = true;
                }else
                    flag = false;
            }    
            return flag;
        }
        
        private void putValueInStateTrack(boolean value) throws IOException{
            File file = new File("state_track.txt");
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(String.valueOf(value));
            filewriter.close();
        }
        
        private void populateListOfBuses(){
            nomLigne.setCellValueFactory(new PropertyValueFactory<Bus, String>("nomLigne"));
            marque.setCellValueFactory(new PropertyValueFactory<Bus, String>("marque"));
            matricule.setCellValueFactory(new PropertyValueFactory<Bus, String>("matricule"));
            capacite.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("capacite"));
            
            busTableView.setItems(Bus.buses);       
        }
        
        private void populateListOfLignes(){
            ligneName.setCellValueFactory(new PropertyValueFactory<Lignes, String>("nomLigne"));
            depart.setCellValueFactory(new PropertyValueFactory<Lignes, String>("depart"));
            arrive.setCellValueFactory(new PropertyValueFactory<Lignes, String>("arrive"));
            prix.setCellValueFactory(new PropertyValueFactory<Lignes, Float>("prix"));
            
            lignesTable.setItems(Lignes.lignes);
        }
        
        private void populateListOfChauffeur(){
            nom.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("nom"));
            prenom.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("prenom"));
            nat.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("Nat"));
            adr.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("Adr"));
            tel.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("Tel"));
            
            chauffeurTable.setItems(Chauffeur.chauffeur);
        }
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        accueil_page.setVisible(true);
        bus_page.setVisible(false);
        ligne_page.setVisible(false);
        chauffeur_page.setVisible(false);
        aide_page.setVisible(false);
        contact_page.setVisible(false);
        reservation_page.setVisible(false);
        
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
        
        exit.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
 
            loadFile("data");
            
            //ADDING LIGNES AS TITLEDPANE AND ALSO ADDING VBOXES
            for(Lignes ligne : Lignes.lignes){              
                    storeVboxes(ligne);
                    lignesList.getPanes().add(new TitledPane(ligne.getNomLigne(), vboxes.get(ligne.getNomLigne())));    
                }
            
            
            AnimationTimer t = new AnimationTimer(){
            @Override
            public void handle(long now) {
                try {
            if(returnHaveIRunValue() == false){
                try {
                    loadDataToDB();
                    putValueInStateTrack(true);
                } catch (SQLException ex) {
                    System.err.println("Could not load buses to DB");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
                
            };t.start();
            
        
            
        //CLOCK AND DATE
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
           
            getEveryLigneBuses();
            
            for(Bus bus : Bus.buses){
                storeButtons(bus);
            }
             
            showBusesOfeveryLigne();
            
            //LOADING SCHEDULE FILE AND PUT IT IN SCHEDULE LIST
            for(Lignes ligne : Lignes.lignes){
            try {
                loadProgramFileOf(ligne);
            } catch (FileNotFoundException ex) {
                System.err.println("Could not find the file !");
            }
            }
            
            //SCHEDULE
            Bus.setEveryBusSchedule();
            
           
                for(Lignes ligne : Lignes.lignes){
                    BusQueue busQueue = new BusQueue(ligne);
                    busQueue.run();
                }
            
            AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                try {
                    reserve();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
                
            };timer.start();
            
            populateListOfBuses();
            populateListOfLignes();
            populateListOfChauffeur();
       
    }
        
}    
    

