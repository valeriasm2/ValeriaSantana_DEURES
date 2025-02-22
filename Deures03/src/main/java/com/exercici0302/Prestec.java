package com.exercici0302;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Prestec {
    private Llibre llibre;
    private String dataPrestec;
    private String dataRetorn;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Prestec(Llibre llibre, String dataPrestec, String dataRetorn) {
        this.llibre = llibre;
        this.dataPrestec = dataPrestec;
        this.dataRetorn = dataRetorn;
    }

    public Llibre getLlibre() {
        return llibre;
    }

    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    public String getDataPrestec() {
        return dataPrestec;
    }

    public void setDataPrestec(String dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public String getDataRetorn() {
        return dataRetorn;
    }

    public void setDataRetorn(String dataRetorn) {
        this.dataRetorn = dataRetorn;
    }

    public boolean estaEnTermini() {
        LocalDate dataRetornLocalDate = LocalDate.parse(dataRetorn, FORMATTER);
        LocalDate dataActual = LocalDate.now();

        return !dataActual.isAfter(dataRetornLocalDate);
    }
}