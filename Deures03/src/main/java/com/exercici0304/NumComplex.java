package com.exercici0304;

public class NumComplex {

    // Atributs
    private double partReal;
    private double partImaginaria;

    // Constructor
    public NumComplex(double partReal, double partImaginaria) {
        this.partReal = partReal;
        this.partImaginaria = partImaginaria;
    }

    // Getters i Setters
    public double getPartReal() {
        return partReal;
    }

    public void setPartReal(double partReal) {
        this.partReal = partReal;
    }

    public double getPartImaginaria() {
        return partImaginaria;
    }

    public void setPartImaginaria(double partImaginaria) {
        this.partImaginaria = partImaginaria;
    }

    // Mètodes d'instància
    public double modul() {
        return Math.sqrt(partReal * partReal + partImaginaria * partImaginaria);
    }

    public NumComplex conjugat() {
        return new NumComplex(partReal, -partImaginaria);
    }

    @Override
    public String toString() {
        String signe = (partImaginaria >= 0) ? " + " : " - ";
        return partReal + signe + Math.abs(partImaginaria) + "i";
    }

    // Mètodes estàtics
    public static NumComplex suma(NumComplex a, NumComplex b) {
        return new NumComplex(a.partReal + b.partReal, a.partImaginaria + b.partImaginaria);
    }

    public static NumComplex resta(NumComplex a, NumComplex b) {
        return new NumComplex(a.partReal - b.partReal, a.partImaginaria - b.partImaginaria);
    }

    public static NumComplex multiplica(NumComplex a, NumComplex b) {
        double real = a.partReal * b.partReal - a.partImaginaria * b.partImaginaria;
        double imaginari = a.partReal * b.partImaginaria + a.partImaginaria * b.partReal;
        return new NumComplex(real, imaginari);
    }

    public static NumComplex divideix(NumComplex a, NumComplex b) {
        double denominador = b.partReal * b.partReal + b.partImaginaria * b.partImaginaria;
        if (denominador == 0) {
            throw new ArithmeticException("No es pot dividir per zero");
        }
        double real = (a.partReal * b.partReal + a.partImaginaria * b.partImaginaria) / denominador;
        double imaginari = (a.partImaginaria * b.partReal - a.partReal * b.partImaginaria) / denominador;
        return new NumComplex(real, imaginari);
    }
}