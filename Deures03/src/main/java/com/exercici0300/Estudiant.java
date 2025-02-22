package com.exercici0300;

public class Estudiant {

    private String nom;
    private String id;

    // Atributs estàtics
    private static int comptadorEstudiants = 0;
    private static final int MAX_ESTUDIANTS = 5;
    
    // Constructor
    public Estudiant(String nom, String id) {
        this.nom = nom;
        this.id = id;
    }

    // Mètodes públics
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    // Mètodes públics estàtics
    public static int getComptadorEstudiants() {
        return comptadorEstudiants;
    }

    public static boolean hiHaCapacitat() {
        return false;
    }
}
