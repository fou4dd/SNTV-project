package sntv;

import javafx.collections.ObservableList;


public class Bus {
    
    private int numBus;
    private String marque;
    private String matricule;
    private int capacite;
       
    boolean isFull;

    public Bus(int NBus, String marque, String matricule, int capacite) {
        this.numBus = numBus;
        this.marque = marque;
        this.matricule = matricule;
        this.capacite = capacite;
    }

    public int getNumBus() {
        return numBus;
    }

    public void setNumBus(int numBus) {
        this.numBus = numBus;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public boolean isIsFull() {
        return isFull;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

    
    
    
    
    
    
}
