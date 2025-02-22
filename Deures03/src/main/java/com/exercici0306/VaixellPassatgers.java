package com.exercici0306;

public class VaixellPassatgers extends Vaixell implements Reglamentari {
    // Atributs
    private int numPassatgers;
    private int maxPassatgers;

    // Constructor
    public VaixellPassatgers(String nom, double capacitat, int maxPassatgers) {
        super(nom, capacitat);
        this.maxPassatgers = maxPassatgers;
        this.numPassatgers = 0;
    }

    // Getters i Setters
    public int getNumPassatgers() {
        return numPassatgers;
    }

    public void setNumPassatgers(int numPassatgers) {
        this.numPassatgers = numPassatgers;
    }

    public int getMaxPassatgers() {
        return maxPassatgers;
    }

    public void setMaxPassatgers(int maxPassatgers) {
        this.maxPassatgers = maxPassatgers;
    }

    // Mètodes
    public void afegirPassatger() {
        if (numPassatgers >= maxPassatgers) {
            throw new IllegalStateException("No es poden afegir més passatgers");
        }
        numPassatgers++;
    }

    @Override
    public boolean compleixNormativa() {
        return numPassatgers <= maxPassatgers;
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "VaixellPassatgers[nom=" + nom + ", capacitat=" + capacitat + ", numPassatgers=" + numPassatgers + ", maxPassatgers=" + maxPassatgers + "]";
    }
}