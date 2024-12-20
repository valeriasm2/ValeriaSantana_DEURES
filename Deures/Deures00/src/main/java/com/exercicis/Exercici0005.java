package com.exercicis;


public class Exercici0005 {

    public static boolean isPalindrom(String text) {
        text = normalize(text);

        int UltLetra = text.length() - 1;
        String invertido = "";
        for (int cnt = UltLetra; cnt >= 0; cnt = cnt - 1) {
            String letra = String.valueOf(text.charAt(cnt));
            invertido = invertido + letra;
        }
        return text.equals(invertido);
    }   

    public static String normalize(String text) {
        String rst = text.toLowerCase();

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

        for (String str : ejemploStrings) {
            System.out.printf("El text '%s' és %s\n", str, isPalindrom(str) ? "un palíndrom" : "no és un palíndrom");
        }
    }
}
