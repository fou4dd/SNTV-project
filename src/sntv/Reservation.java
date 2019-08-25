package sntv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Reservation {
    
    private Lignes ligne;
    private Bus bus;
    private Passenger passenger;  
    private int resCount;
    
    public static ObservableList<Reservation> reservation = FXCollections.observableArrayList();

    public Reservation(Lignes ligne, Bus bus, Passenger passenger, int resCount) {
        this.ligne = ligne;
        this.bus = bus;
        this.passenger = passenger;
        this.resCount++;
    }

    public Reservation(Lignes ligne, Bus bus, Passenger passenger) {
        this.ligne = ligne;
        this.bus = bus;
        this.passenger = passenger;
        this.resCount++;
    }
    
    
    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public int getResCount() {
        return resCount;
    }

    public void setResCount(int resCount) {
        this.resCount = resCount;
    }
    
    
}
