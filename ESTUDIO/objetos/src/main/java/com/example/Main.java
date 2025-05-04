package com.example; // Paquete de la aplicación

public class Main {
    public static void main(String[] args) {

        // ***************** INSTANCIACIÓN DE OBJETOS *****************

        // Creamos un objeto usando el constructor vacío
        Alumno alu1 = new Alumno();

        // Creamos otro objeto usando el constructor con parámetros
        Alumno alu2 = new Alumno(5, "Val", "Santa");

        // ***************** ENCAPSULAMIENTO: USO DE GETTERS *****************

        System.out.println("****************** GETTERS ********************");
        System.out.println("ID del alumno 2: " + alu2.getId());
        System.out.println("Nombre: " + alu2.getNombre());
        System.out.println("Apellido: " + alu2.getApellido());

        // ***************** ENCAPSULAMIENTO: USO DE SETTERS *****************

        System.out.println("\n****************** SETTERS ********************");
        alu1.setId(3);
        alu1.setNombre("Juan");
        alu1.setApellido("Santana");

        System.out.println("ID del alumno 1: " + alu1.getId());
        System.out.println("Nombre: " + alu1.getNombre());
        System.out.println("Apellido: " + alu1.getApellido());

        // ***************** MODIFICAR VALORES DE UN OBJETO *****************

        System.out.println("\n****************** MODIFICAR VALOR ********************");
        alu2.setId(34); // Cambiamos el ID del alumno 2

        System.out.println("Nuevo ID del alumno 2: " + alu2.getId());

        // ***************** ABSTRACCIÓN: USO DE MÉTODOS *****************

        System.out.println("\n****************** MÉTODOS PERSONALIZADOS ********************");
        alu1.mostrarNombre(); // Método que representa un comportamiento
        alu2.aprobado(7.5);   // Comportamiento que evalúa una nota

        // ***************** POLIMORFISMO: MÉTODO toString() *****************

        System.out.println("\n****************** MÉTODO toString() ********************");
        System.out.println(alu1.toString());
        System.out.println(alu2.toString());

    }
}
