package com.exercicis;

import java.util.ArrayList;
import java.util.Random;

public class Exercici0010 {

    public static ArrayList<Integer> generarNumerosAleatoris(int quantitat, int min, int max) {
        
        ArrayList<Integer> numeros = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < quantitat; i++) {
            numeros.add(random.nextInt(max - min + 1) + min);
        }
        return numeros;
    }

    public static ArrayList<Integer> multiplicarPerDos(ArrayList<Integer> llista) {
        
        ArrayList<Integer> resultat = new ArrayList<>();
        for (int num : llista) {
            resultat.add(num * 2);
        }
        return resultat;
    }

    public static ArrayList<Integer> filtrarImparells(ArrayList<Integer> llista) {
        
        ArrayList<Integer> imparells = new ArrayList<>();
        for (int num : llista) {
            if (num % 2 != 0) {
                imparells.add(num);
            }
        }
        return imparells;
    }

    public static ArrayList<Double> dividirPerDos(ArrayList<Integer> llista) {
        
        ArrayList<Double> resultat = new ArrayList<>();
        for (int num : llista) {
            resultat.add(num / 2.0);
        }
        return resultat;
    }

    public static void main(String[] args) {

        ArrayList<Integer> llistaAleatoria = generarNumerosAleatoris(10, 0, 99);
        System.out.println("Llista inicial: " + llistaAleatoria);

        ArrayList<Integer> llistaMultiplicada = multiplicarPerDos(llistaAleatoria);
        System.out.println("Llista multiplicada per 2: " + llistaMultiplicada);

        ArrayList<Integer> llistaImparells = filtrarImparells(llistaAleatoria);
        System.out.println("Llista d'imparells: " + llistaImparells);

        ArrayList<Double> llistaDividida = dividirPerDos(llistaAleatoria);
        System.out.println("Llista imparells dividits per 2: " + llistaDividida);
        
    }
}
