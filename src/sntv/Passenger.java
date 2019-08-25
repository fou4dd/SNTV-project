package sntv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Passenger {
    
    private String firstName;
    private String lastName;
    private String idNumber;
    
    public static ObservableList<Passenger> passenger = FXCollections.observableArrayList();

    public Passenger(String firstName, String lastName, String idNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    
    
}
