package com.exercici0601;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import com.utils.UtilsViews;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Controlador para la vista de juegos de Nintendo.
 * Maneja la visualización y selección de juegos con sus detalles.
 */
public class ControllerJocs {

    // ======================
    // COMPONENTES DE LA INTERFAZ
    // ======================
    
    @FXML
    private ChoiceBox<String> choiceBoxJocs; // Menú desplegable para seleccionar juegos
    
    @FXML
    private Label lblNme; // Muestra el nombre del juego
    
    @FXML
    private Label lblAny; // Muestra el año de lanzamiento
    
    @FXML
    private Label lblTipo; // Muestra el tipo/género del juego
    
    @FXML
    private Label lblPlot; // Muestra la descripción/argumento del juego
    
    @FXML
    private ImageView imgJocs; // Muestra la imagen del juego
    
    @FXML
    private ImageView imgArrowBack; // Botón para volver al menú principal

    private JSONArray games; // Almacena los datos de los juegos cargados del JSON

    // ======================
    // MÉTODOS PRINCIPALES
    // ======================

    /**
     * Maneja el evento de clic para volver a la vista principal.
     */
    @FXML
    private void handleBackToMain() {
        try {
            UtilsViews.setViewAnimating("ViewMain");
        } catch (Exception e) {
            System.err.println("Error al volver al menú principal:");
            e.printStackTrace();
        }
    }

    /**
     * Método de inicialización que se ejecuta al cargar la vista.
     * Carga los datos de los juegos y configura los componentes.
     */
    @FXML
    public void initialize() {
        try {
            // 1. Cargar el archivo JSON de juegos desde los recursos
            InputStream is = getClass().getResourceAsStream("/assets/data/games.json");
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            games = new JSONArray(content);

            // 2. Limpiar y llenar el ChoiceBox con los nombres de los juegos
            choiceBoxJocs.getItems().clear();
            for (int i = 0; i < games.length(); i++) {
                String gameName = games.getJSONObject(i).getString("name");
                choiceBoxJocs.getItems().add(gameName);
            }

            // 3. Configurar el listener para detectar cambios de selección
            choiceBoxJocs.setOnAction(e -> {
                String selectedGame = choiceBoxJocs.getValue();
                if (selectedGame != null) {
                    updateGameDetails(selectedGame);
                }
            });

            // 4. Seleccionar y mostrar el primer juego por defecto (si existe)
            if (games.length() > 0) {
                choiceBoxJocs.setValue(games.getJSONObject(0).getString("name"));
                updateGameDetails(choiceBoxJocs.getValue());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los juegos:");
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los detalles del juego seleccionado.
     * @param gameName Nombre del juego cuyos detalles se mostrarán
     */
    private void updateGameDetails(String gameName) {
        try {
            // Buscar el juego seleccionado en el JSON
            for (int i = 0; i < games.length(); i++) {
                JSONObject game = games.getJSONObject(i);
                if (game.getString("name").equals(gameName)) {
                    // Actualizar los labels con la información del juego
                    lblNme.setText(game.getString("name"));
                    lblAny.setText(String.valueOf(game.getInt("year")));
                    lblTipo.setText(game.getString("type"));
                    lblPlot.setText(game.getString("plot"));

                    // Cargar y mostrar la imagen del juego
                    String imagePath = "/assets/images0601/" + game.getString("image");
                    InputStream imgStream = getClass().getResourceAsStream(imagePath);
                    if (imgStream != null) {
                        Image image = new Image(imgStream);
                        imgJocs.setImage(image);
                    } else {
                        System.err.println("No se pudo cargar la imagen: " + imagePath);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar detalles del juego:");
            e.printStackTrace();
        }
    }
}