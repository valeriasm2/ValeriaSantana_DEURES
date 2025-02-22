package com.exercici0306;

import java.util.ArrayList;

public class Port {
    // Atributs
    private String nom;
    private ArrayList<Vaixell> vaixells;

    // Constructor
    public Port(String nom) {
        this.nom = nom;
        this.vaixells = new ArrayList<>();
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Mètodes
    public void afegirVaixell(Vaixell v) {
        vaixells.add(v);
    }

    public ArrayList<Vaixell> getVaixells() {
        return vaixells;
    }

    public void printVaixells() {
        for (Vaixell v : vaixells) {
            System.out.println(v.toString());
        }
    }

    public ArrayList<String> validarNormatives() {
        ArrayList<String> resultat = new ArrayList<>();
        for (Vaixell v : vaixells) {
            if (v instanceof Reglamentari) {
                boolean compleix = ((Reglamentari) v).compleixNormativa();
                resultat.add(v.getNom() + " (" + v.getClass().getSimpleName() + "): " + (compleix ? "Compleix" : "No compleix"));
            }
        }
        return resultat;
    }

    public void printNormatives() {
        ArrayList<String> normatives = validarNormatives();
        for (String n : normatives) {
            System.out.println(n);
        }
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "Port[nom=" + nom + "]";
    }
}