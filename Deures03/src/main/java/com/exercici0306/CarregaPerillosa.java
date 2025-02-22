package com.exercici0306;

public class CarregaPerillosa extends Carrega {
    // Atributs
    private int nivellPerillositat;

    // Constructor
    public CarregaPerillosa(String descripcio, double pes, int nivellPerillositat) {
        super(descripcio, pes);
        setNivellPerillositat(nivellPerillositat);
    }

    // Getters i Setters
    public int getNivellPerillositat() {
        return nivellPerillositat;
    }

    public void setNivellPerillositat(int nivellPerillositat) {
        if (nivellPerillositat < 0 || nivellPerillositat > 5) {
            throw new IllegalArgumentException("El nivell de perillositat ha de ser entre 0 i 5");
        }
        this.nivellPerillositat = nivellPerillositat;
    }

    // Mètode toString()
    @Override
    public String toString() {
        return "CarregaPerillosa[descripcio=" + descripcio + ", pes=" + pes + ", nivellPerillositat=" + nivellPerillositat + "]";
    }
}