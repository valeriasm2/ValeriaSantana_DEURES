package com.example;

// Clase Alumno que representa una entidad con propiedades y comportamientos
public class Alumno {

    // ************ ENCAPSULAMIENTO ************
    // Atributos privados o con acceso limitado (mejor práctica: private)
    private int id;
    private String nombre;
    private String apellido;

    // ************ CONSTRUCTORES ************
    
    // Constructor por defecto
    public Alumno() {
        // Inicializa atributos con valores por defecto (opcional)
    }

    // Constructor con parámetros
    public Alumno(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // ************ GETTERS Y SETTERS ************

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // ************ MÉTODOS PERSONALIZADOS ************

    // Método que simula una acción del objeto
    public void mostrarNombre() {
        System.out.println("Hola, soy un alumno y sé decir mi nombre: " + nombre);
    }

    // Método que determina si el alumno aprobó con base en su nota
    public void aprobado(double nota) {
        if (nota >= 6) {
            System.out.println("Aprobé la materia");
        } else {
            System.out.println("No aprobé la materia");
        }
    }

    // ************ POLIMORFISMO ************
    // Sobrescribimos el método toString() para mostrar info del objeto de forma amigable
    @Override
    public String toString() {
        return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + "]";
    }
}