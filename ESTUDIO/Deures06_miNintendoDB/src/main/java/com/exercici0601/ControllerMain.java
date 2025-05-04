package com.exercici0601;

import com.utils.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Controlador principal de la aplicación Nintendo DB.
 * Gestiona la navegación entre las diferentes vistas y la carga del logo.
 */
public class ControllerMain {

    // ======================
    // COMPONENTES DE LA VISTA
    // ======================

    @FXML
    private ImageView logoImageView; // Componente para mostrar el logo de Nintendo

    // ======================
    // MÉTODOS DE INICIALIZACIÓN
    // ======================

    /**
     * Método de inicialización que se ejecuta al cargar la vista.
     * Carga y muestra el logo de Nintendo.
     */
    @FXML
    public void initialize() {
        try {
            // Verificación de la ruta de la imagen (para depuración)
            System.out.println("Intentando cargar imagen desde: " +
                    getClass().getResource("/images0601/nintendo-icon.png"));

            // Cargar la imagen del logo desde los recursos
            Image logo = new Image(getClass().getResourceAsStream("/images0601/nintendo-icon.png"));
            logoImageView.setImage(logo);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            // Cargar imagen de placeholder si falla la carga del logo
            logoImageView.setImage(new Image("https://via.placeholder.com/50"));
        }
    }

    // =============================
    // | MANEJADORES DE NAVEGACIÓN |
    // =============================

    /**
     * Maneja el evento de clic para ir a la vista de personajes.
     * @param event Evento del ratón
     */
    @FXML
    private void toViewCharacters(MouseEvent event) {
        System.out.println("To View Characters");
        // Obtener el controlador de la vista de personajes
        ControllerCharacters ctrlCharacters = (ControllerCharacters) UtilsViews.getController("ViewCharacters");
        // Cargar la lista de personajes
        ctrlCharacters.loadList();
        // Navegar a la vista de personajes
        UtilsViews.setViewAnimating("ViewCharacters");
    }

    /**
     * Maneja el evento de clic para ir a la vista de juegos.
     * @param event Evento del ratón
     */
    @FXML
    private void toViewGames(MouseEvent event) {
        System.out.println("To View Games");
        // Obtener el controlador de la vista de juegos
        ControllerJocs ctrlGames = (ControllerJocs) UtilsViews.getController("ViewGames");
        // Navegar a la vista de juegos
        UtilsViews.setViewAnimating("ViewGames");
    }

    /**
     * Maneja el evento de clic para ir a la vista de consolas.
     * @param event Evento del ratón
     */
    @FXML
    private void toViewConsoles(MouseEvent event) {
        System.out.println("To View Consoles");
        // Obtener el controlador de la vista de consolas
        ControllerConsoles ctrlConsoles = (ControllerConsoles) UtilsViews.getController("ViewConsoles");
        // Navegar a la vista de consolas
        UtilsViews.setViewAnimating("ViewConsoles");
    }
}