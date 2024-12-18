package com.exercicis;

import java.util.Locale;
import java.util.Scanner;

public class Exercici0001 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locale localeUS = Locale.US;

        System.out.print("Escriu el pes (kg): ");
        float pes = scanner.nextFloat();

        System.out.print("Escriu l'alçada (cm):");
        float alturaCm = scanner.nextFloat();


        float alturaMetres = alturaCm / 100;
        double imc = pes / (alturaMetres * alturaMetres);

        System.out.printf(localeUS, "imc = %.2f%n", imc);        

        scanner.close();
    }
}
