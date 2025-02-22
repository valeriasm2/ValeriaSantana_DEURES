package com.exercici0306;

public class Carrega {
    // Atributs
    protected String descripcio;
    protected double pes;

    // Constructor
    public Carrega(String descripcio, double pes) {
        this.descripcio = descripcio;
        this.pes = pes;
    }

    // Getters i Setters
    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getPes() {
        return pes;
    }

    public void setPes(double pes) {
        this.pes = pes;
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "Carrega[descripcio=" + descripcio + ", pes=" + pes + "]";
    }
}