package com.exercici0305;

public class Arbitre extends Participant implements Competidor {

    // Atributs
    private String nivell;

    // Constructor
    public Arbitre(String nom, int edat, String nivell) {
        super(nom, edat);
        this.nivell = nivell; 
    }

    // Getters i Setters
    public String getNivell() {
        return nivell;
    }

    public void setNivell(String nivell) {
        this.nivell = nivell;
    }

    // Implementació del mètode de la interfície Competidor
    @Override
    public void competir() {
        System.out.println("Supervisant competició");
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "Arbitre[nom=" + getNom() + ", edat=" + getEdat() + ", nivell=" + nivell + "]";
    }
}