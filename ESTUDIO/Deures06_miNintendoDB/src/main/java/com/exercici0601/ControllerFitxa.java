package com.exercici0601;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import com.utils.UtilsViews;

/**
 * Controlador para la vista de ficha detallada de personajes (Ficha técnica)
 * Muestra información ampliada de un personaje seleccionado
 */
public class ControllerFitxa {

    // ======================
    // ELEMENTOS DE LA VISTA
    // ======================
    
    @FXML private Text name;        // Texto para mostrar el nombre del personaje
    @FXML private Text game;        // Texto para mostrar el juego asociado
    @FXML private ImageView imgCharacter;  // Componente para mostrar la imagen del personaje
    @FXML private Circle color;     // Círculo que muestra el color representativo
    @FXML private ImageView imgArrowBack;  // Botón de flecha para volver atrás

    // ======================
    // MÉTODOS PRINCIPALES
    // ======================

    /**
     * Carga los datos del personaje en los componentes de la vista
     * @param name Nombre del personaje
     * @param game Juego al que pertenece
     * @param imagePath Ruta relativa de la imagen (dentro de /assets/images0601/)
     * @param colorHex Color representativo en formato hexadecimal (#RRGGBB)
     */
    public void setCharacterData(String name, String game, String imagePath, String colorHex) {
        // Establecer textos
        this.name.setText(name);
        this.game.setText(game);
        
        try {
            // Cargar imagen desde recursos
            Image img = new Image(getClass().getResourceAsStream("/assets/images0601/" + imagePath));
            imgCharacter.setImage(img);
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            // Nota: Podría añadirse una imagen por defecto aquí
        }
        
        try {
            // Establecer color del círculo
            color.setFill(Color.web(colorHex));
        } catch (IllegalArgumentException e) {
            // Manejo de color hexadecimal inválido
            color.setFill(Color.GRAY); // Color por defecto
            System.err.println("Color inválido, usando gris por defecto: " + colorHex);
        }
    }


    /**
     * Maneja el evento de clic para volver a la vista principal
     * @param event Evento del ratón
     */
    @FXML
    private void handleBackToMain(MouseEvent event) {
        try {
            UtilsViews.setViewAnimating("ViewMain");
        } catch (Exception e) {
            System.err.println("Error al volver al menú principal:");
            e.printStackTrace();
        }
    }

    /**
     * Alternativa para navegar a la vista principal
     * @param event Evento del ratón
     */
    @FXML
    private void toViewMain(MouseEvent event) {
        UtilsViews.setViewAnimating("ViewCharacters");
    }
}