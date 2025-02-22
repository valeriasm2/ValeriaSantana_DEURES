package com.exercici0301;

public class ControlTemperatura {

    private String nomZona;
    private double temperatura;

    private static double temperaturaTotal = 0.0;
    private static int comptadorZones = 0;

    // Constructor
    public ControlTemperatura(String nomZona, double temperatura) {
        this.nomZona = nomZona;
        this.temperatura = temperatura;
        temperaturaTotal += temperatura;
        comptadorZones++;
    }

    // Ajustar temperatura
    public void ajustaTemperatura(double novaTemperatura) {
        temperaturaTotal -= this.temperatura; // antiga temperatura
        this.temperatura = novaTemperatura;  // nova temperatura
        temperaturaTotal += novaTemperatura; // Sumem la nova temperatura
    }

    // Mètode estàtic
    public static double getTemperaturaMitjana() {
        return (comptadorZones == 0) ? 0.0 : temperaturaTotal / comptadorZones;
    }

    // Getters
    public String getNomZona() {
        return nomZona;
    }

    public double getTemperatura() {
        return temperatura;
    }
}
