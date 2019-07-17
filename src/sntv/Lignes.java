
package sntv;

import javafx.collections.ObservableList;


public class Lignes {
    
    private int numLignes;
    private String nomLigne;
    private String depart;
    private String arrive;
    private float prix;
    
    ObservableList<Bus> listDesBus;

    public Lignes(int numLignes, String nomLigne, String depart, String arrive, float prix) {
        this.numLignes = numLignes;
        this.nomLigne = nomLigne;
        this.depart = depart;
        this.arrive = arrive;
        this.prix = prix;
    }

    public Lignes(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    public int getNumLignes() {
        return numLignes;
    }

    public void setNumLignes(int numLignes) {
        this.numLignes = numLignes;
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    
    
    
}
