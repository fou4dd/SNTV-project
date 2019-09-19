/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sntv;

import static DB.DataBase.connect;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author fou
 */
public class FXMLloginController implements Initializable {
    
    @FXML JFXTextField usernameID;
    @FXML JFXPasswordField passwordID;
    @FXML ImageView substract;
    private double xOffset = 0;
    private double yOffset = 0;
    
    public void login(Event e) throws IOException{
        
        Connection connection = connect();
        ObservableList<User> list = FXCollections.observableArrayList();
        
        try {
            PreparedStatement ps = connection.prepareStatement("select * from Login");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(new User(rs.getString("username"), rs.getString("pass")));
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
        
        for(User user : list){
            if(usernameID.getText().trim().equals(user.getUsername()) && passwordID.getText().trim().equals(user.getPassword())){
                
                Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
                
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                         xOffset = event.getSceneX();
                         yOffset = event.getSceneY();
                    }
                });
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
                });
                
                stage.setScene(scene);
                
                System.out.println("LOGIN");
                break;
            
           
               
            }
            
 
        }

    }
    
      public void exit(){
        Platform.exit();
    }    
        
        
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }  
    
    
    }
   


