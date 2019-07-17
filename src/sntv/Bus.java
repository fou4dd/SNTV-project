package sntv;

import javafx.collections.ObservableList;


public class Bus {
    
    private int NBus;
    private int NVoyage;
    private int Capacite;
    private String nom;
    
    public static ObservableList<Bus> availlableBus;
    
    boolean isFull;

    public Bus(int NBus, int NVoyage, int Capacite, String nom) {
        this.NBus = NBus;
        this.NVoyage = NVoyage;
        this.Capacite = Capacite;
        this.nom = nom;
    }

    public int getNBus() {
        return NBus;
    }

    public void setNBus(int NBus) {
        this.NBus = NBus;
    }

    public int getNVoyage() {
        return NVoyage;
    }

    public void setNVoyage(int NVoyage) {
        this.NVoyage = NVoyage;
    }

    public int getCapacite() {
        return Capacite;
    }

    public void setCapacite(int Capacite) {
        this.Capacite = Capacite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
    
    
}
