package sntv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class Chauffeur {
    
    private int NChauffeur;
    private String nom;
    private String prenom;
    private String Nat;
    private String Adr;
    private String Tel;
    
    public static ObservableList<Chauffeur> chauffeur = FXCollections.observableArrayList();

    public Chauffeur(int NChauffeur, String nom, String prenom, String Nat, String Adr, String Tel) {
        this.NChauffeur = NChauffeur;
        this.nom = nom;
        this.prenom = prenom;
        this.Nat = Nat;
        this.Adr = Adr;
        this.Tel = Tel;
    }

    public Chauffeur(String nom, String prenom, String Nat, String Adr, String Tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.Nat = Nat;
        this.Adr = Adr;
        this.Tel = Tel;
    }

    public int getNChauffeur() {
        return NChauffeur;
    }

    public void setNChauffeur(int NChauffeur) {
        this.NChauffeur = NChauffeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNat() {
        return Nat;
    }

    public void setNat(String Nat) {
        this.Nat = Nat;
    }

    public String getAdr() {
        return Adr;
    }

    public void setAdr(String Adr) {
        this.Adr = Adr;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }
    
    
    
}
