package com.exercici0306;

public class VaixellMercaderies extends Vaixell implements Reglamentari {
    // Atributs
    private String paisRegistre;

    // Constructor
    public VaixellMercaderies(String nom, double capacitat, String paisRegistre) {
        super(nom, capacitat);
        this.paisRegistre = paisRegistre;
    }

    // Getters i Setters
    public String getPaisRegistre() {
        return paisRegistre;
    }

    public void setPaisRegistre(String paisRegistre) {
        this.paisRegistre = paisRegistre;
    }

    @Override
    public boolean compleixNormativa() {
        return getPesTotal() <= getCapacitat();
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "VaixellMercaderies[nom=" + nom + ", capacitat=" + capacitat + ", paisRegistre=" + paisRegistre + "]";
    }
}