package sntv;


public class Voyage {
    
    private Bus bus;
    private Lignes ligne;
    private Chauffeur chauffeur;
    private String startDate;

    public Voyage(Bus bus, Lignes ligne, Chauffeur chauffeur, String startDate) {
        this.bus = bus;
        this.ligne = ligne;
        this.chauffeur = chauffeur;
        this.startDate = startDate;
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

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    
    
}
