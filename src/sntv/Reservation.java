package sntv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Reservation {
    
    private Voyage voyage;
    private Passenger passenger;  
    
    public static ObservableList<Reservation> reservation = FXCollections.observableArrayList();

    public Reservation(Voyage voyage, Passenger passenger) {
        this.voyage = voyage;
        this.passenger = passenger;
    }

    public Reservation() {
        
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

}
