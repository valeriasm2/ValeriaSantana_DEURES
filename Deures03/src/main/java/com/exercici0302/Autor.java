package com.exercici0302;

public class Autor {

    // Atributs
    private String nom;
    private String nacionalitat;

    // Constructors
    public Autor(String nom, String nacionalitat) {
        this.nom = nom;
        this.nacionalitat = nacionalitat;
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNacionalitat() {
        return nacionalitat;
    }

    public void setNacionalitat(String nacionalitat) {
        this.nacionalitat = nacionalitat;
    }

}

