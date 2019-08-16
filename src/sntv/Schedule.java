package sntv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Schedule {
    
    private String matricule;
    private int priopity;
    private String loadTime;
    private String startTime;
    public int timeBetween;
    
    public static ObservableList<Schedule> schedule = FXCollections.observableArrayList();

    public Schedule(String matricule, int priopity, String loadTime, String startTime) {
        this.matricule = matricule;
        this.priopity = priopity;
        this.loadTime = loadTime;
        this.startTime = startTime;
    }
    
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setTimeBetween(int timeBetween) {
        this.timeBetween = timeBetween;
    }
    
    public int getPriopity() {
        return priopity;
    }

    public void setPriopity(int priopity) {
        this.priopity = priopity;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTimeBetween() {
        return timeBetween;
    }
  
}
