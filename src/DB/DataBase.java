/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author fou
 */
public class DataBase {
    
    public static Connection connect(){
        
        Connection connection = null;
        
        try{
            
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection("jdbc:ucanaccess://BDDSNTV.accbd");
            JOptionPane.showMessageDialog(null, "Connection Succesful!");
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Connection failed!");
            
        }
        
        return connection;
    } 
    
}
