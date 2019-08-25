package sntv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Bus {
    
    private int numBus;
    private String nomLigne;
    private String marque;
    private String matricule;
    private int capacite;
    private Schedule schedule;
    public int resNumber = 0;
    private boolean full = false;
    
    public static ObservableList<Bus> buses = FXCollections.observableArrayList();

    public Bus(int numBus, String nomLigne, String marque, String matricule, int capacite) {
        this.numBus = numBus;
        this.nomLigne = nomLigne;
        this.marque = marque;
        this.matricule = matricule;
        this.capacite = capacite;
    }

    public Bus(String nomLigne, String marque, String matricule, int capacite) {
        this.nomLigne = nomLigne;
        this.marque = marque;
        this.matricule = matricule;
        this.capacite = capacite;
    }
    
    public static void setEveryBusSchedule(){
        for(Bus bus : Bus.buses){
            for(Schedule schedule : Schedule.schedule){
                if(bus.getMatricule().equals(schedule.getMatricule())){
                    bus.setSchedule(schedule);
                }
            }
        }
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public int getNumBus() {
        return numBus;
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
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

    public boolean isFull() {
        if(this.capacite == this.resNumber){
            this.full = true;
        }else
            this.full = false;
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    

    
    
    
    
    
    
}
