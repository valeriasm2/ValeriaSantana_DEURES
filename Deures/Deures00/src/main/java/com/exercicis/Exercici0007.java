package com.exercicis;

import java.util.Locale;

public class Exercici0007 {

    public static double calculaCostLloc(String tipusLloc) {
        return switch (tipusLloc.toLowerCase()) {
            case "sala estàndard" -> 100.0;
            case "jardí amb piscina" -> 200.0;
            case "saló gran amb escenari" -> 500.0;
            default -> throw new IllegalArgumentException("Tipus de lloc desconegut: " + tipusLloc);
        };
    }

    public static double calculaCostMenjar(String tipusMenjar, int numConvidats) {
        double precioPerConvidat = switch (tipusMenjar.toLowerCase()) {
            case "menú bàsic" -> 15.0;
            case "menú premium" -> 30.0;
            default -> throw new IllegalArgumentException("Tipus de menú desconegut: " + tipusMenjar);
        };
    }

    public static double calculaCostEntreteniment(String tipusEntreteniment) {
        /*
            TODO: Resol aquí la funció
        */
        return 0.0;
    }

    public static double calculaFesta(String tipusLloc, String tipusMenjar, String tipusEntreteniment, int numConvidats) {
        /*
            TODO: Resol aquí la funció
        */
        return 0.0;
    }

    public static void main(String[] args) {
        /*
            TODO: Resol aquí l'exercici
        */
    }
}
