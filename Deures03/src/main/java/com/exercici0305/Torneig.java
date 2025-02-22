package com.exercici0305;

import java.util.ArrayList;

public class Torneig {

    // Atributs
    private ArrayList<Participant> participants;

    // Constructor
    public Torneig() {
        this.participants = new ArrayList<>();
    }

    // Mètodes

    public void afegirParticipant(Participant p) {
        participants.add(p);
    }


    public void competir() {
        for (Participant p : participants) {
            if (p instanceof Competidor) {
                ((Competidor) p).competir();
            }
        }
    }


    public void entrenar() {
        for (Participant p : participants) {
            if (p instanceof Esportista) {
                ((Esportista) p).entrenar();
            }
        }
    }


    public ArrayList<Participant> getParticipants() {
        return participants;
    }


    public void printParticipants() {
        for (Participant p : participants) {
            System.out.println(p.toString());
        }
    }
}