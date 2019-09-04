package sntv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Voyage {
    
    private Bus bus;
    private Lignes ligne;
    private String startDate;
    
    public static ObservableList<Voyage> voyages = FXCollections.observableArrayList();

    public Voyage(Bus bus, Lignes ligne, String startDate) {
        this.bus = bus;
        this.ligne = ligne;
        this.startDate = startDate;
    }

    public Voyage() {
    }
    
    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    
    
}

