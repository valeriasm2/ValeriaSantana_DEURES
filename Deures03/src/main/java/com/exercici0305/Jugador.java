package com.exercici0305;

public class Jugador extends Participant implements Esportista, Competidor {

    // Atributs
    private String equip;

    // Constructor
    public Jugador(String nom, int edat, String equip) {
        super(nom, edat); // Crida al constructor de la classe pare (Participant)
        this.equip = equip; // Inicialitza l'atribut equip
    }

    // Getters i Setters
    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    // Implementació dels mètodes de les interfícies
    @Override
    public void entrenar() {
        System.out.println("Entrenant com a jugador");
    }

    @Override
    public void competir() {
        System.out.println("Competint com a jugador");
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "Jugador[nom=" + getNom() + ", edat=" + getEdat() + ", equip=" + equip + "]";
    }
}