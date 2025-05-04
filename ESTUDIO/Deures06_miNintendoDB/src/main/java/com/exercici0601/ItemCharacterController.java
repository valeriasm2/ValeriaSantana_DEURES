package com.exercici0601;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Controlador para los elementos individuales de la lista de personajes.
 */
public class ItemCharacterController {

    @FXML private ImageView imgCharacter;
    @FXML private Label lblName;
    @FXML private Label lblGame;
    @FXML private Circle circleColor;

    /**
     * Asigna los datos visuales del personaje.
     */
    public void setData(String name, String game, String imagePath, String color) {
        lblName.setText(name);
        lblGame.setText(game);
        loadCharacterImage(imagePath);
        setCharacterColor(color);
    }

    /**
     * Carga la imagen del personaje (sin manejo de errores).
     */
    private void loadCharacterImage(String imagePath) {
        Image image = new Image(
            getClass().getResourceAsStream("/assets/images0601/" + imagePath),
            50, 50, true, true
        );
        imgCharacter.setImage(image);
    }

    /**
     * Aplica el color representativo del personaje.
     */
    private void setCharacterColor(String color) {
        circleColor.setFill(Color.web(color));
    }
}
