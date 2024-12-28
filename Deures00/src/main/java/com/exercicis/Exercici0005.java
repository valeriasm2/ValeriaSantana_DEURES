package com.exercicis;


public class Exercici0005 {

    public static boolean isPalindrom(String text) {
        text = normalize(text);

        int UltLetra = text.length() - 1;
        StringBuilder invertido = new StringBuilder();
        for (int cnt = UltLetra; cnt >= 0; cnt--) {
            char letra = text.charAt(cnt);
            invertido.append(letra);
        }
        return text.equals(invertido.toString());
    }

    public static String normalize(String text) {
        String rst = text.toLowerCase();

        // Añadimos un reemplazo explícito para el carácter "·"
        String[] accentos = {"à", "á", "è", "é", "í", "ò", "ó", "ú", "ù", " ", "'", "!", "\\.", ",", "·"};
        String[] sinAccentos = {"a", "a", "e", "e", "i", "o", "o", "u", "u", "", "", "", "", "", ""};

        for (int cnt = 0; cnt < accentos.length; cnt++) {
            rst = rst.replaceAll(accentos[cnt], sinAccentos[cnt]);
        }
        return rst;
    }

    public static void main(String[] args) {
        String[] ejemploStrings = {
            "Anul·la la lluna",
            "Atrapa la lluna",
            "Atrapa'l o l'aparta",
            "Aparta'l o atrapa'l",
            "No sap pas on",
            "On sap pas qui",
            "Tramaran anar a Mart",
            "A Mart trobaràn art",
            "Un pop nu",
            "Nu pop un"
        };

        for (String text : ejemploStrings) {
            boolean esPalindrom = isPalindrom(text);
            System.out.println(text + " (" + (esPalindrom ? "Si" : "No") + ")");
        }
    }
}
