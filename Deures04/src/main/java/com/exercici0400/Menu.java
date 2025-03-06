package com.exercici0400;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu extends Component {

    private HashMap<Integer, MenuItem> items;
    private boolean lastZero;

    public Menu(int x, int y, int width, int height, String title, HashMap<Integer, MenuItem> items, boolean lastZero) {
        super(x, y, width, height, title);
        this.items = items;
        this.lastZero = lastZero;
    }

    public void setLastZero(boolean value) {
        this.lastZero = value;
    }

    public boolean getLastZero() {
        return lastZero;
    }

    private String fixLine(String line) {
        if (line.length() < width) {
            String espais = " ".repeat(width - line.length());
            return line + espais;
        } else {
            return line.substring(0, width);
        }
    }

    public ArrayList<String> render() {
        ArrayList<String> rst = new ArrayList<String>();
        boolean doneZero = false;

        int begin = 0;
        if (lastZero) {
            begin = 1;
        }

        rst.add(" ".repeat(width));  // Añadir una línea vacía (probablemente para el encabezado)
        for (int i = begin; i < height; i++) {
            if (items.get(i) != null) {
                String linia = " " + i + "." + items.get(i).getTitle();
                rst.add(fixLine(linia));
            } else {
                if (lastZero && !doneZero && items.get(0) != null) {
                    String line = " " + "0." + items.get(0).getTitle();
                    rst.add(fixLine(line));
                    doneZero = true;
                }
                String linia = " ".repeat(width);  // Añadir líneas vacías si no hay opciones
                rst.add(linia);
            }
        }

        return rst;
    }

    // Método para manejar la selección del usuario
    public int getSelection() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Mostrar opciones
        System.out.println("Selecciona una opción del menú:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(i + ". " + items.get(i).getTitle());
        }

        // Capturar la entrada del usuario y verificar si es válida
        while (choice < 0 || choice >= items.size()) {
            System.out.print("Selecciona una opción entre 0 y " + (items.size() - 1) + ": ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next(); // Limpiar el buffer si no es un número
            }
        }
        
        return choice;  // Retorna la opción seleccionada
    }
}