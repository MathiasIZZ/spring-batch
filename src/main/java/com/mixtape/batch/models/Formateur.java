package com.mixtape.batch.models;


public class Formateur {

    private int id;
    private String nom;
    private String prenom;
    private String adresseMail;


    public Formateur(int id, String nom, String prenom, String adresseMail) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
    }
}
