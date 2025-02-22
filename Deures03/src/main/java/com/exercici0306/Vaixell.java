package com.exercici0306;

import java.util.ArrayList;

public class Vaixell implements Transportable {
    // Atributs
    protected String nom;
    protected double capacitat;
    protected ArrayList<Carrega> carrega;

    // Constructor
    public Vaixell(String nom, double capacitat) {
        this.nom = nom;
        this.capacitat = capacitat;
        this.carrega = new ArrayList<>();
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(double capacitat) {
        this.capacitat = capacitat;
    }

    // Mètodes
    public void afegirCarrega(Carrega c) {
        carrega.add(c);
    }

    public ArrayList<Carrega> getCarregues() {
        return carrega;
    }

    @Override
    public double getPesTotal() {
        double pesTotal = 0;
        for (Carrega c : carrega) {
            pesTotal += c.getPes();
        }
        return pesTotal;
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "Vaixell[nom=" + nom + ", capacitat=" + capacitat + ", pesActual=" + getPesTotal() + "]";
    }
}