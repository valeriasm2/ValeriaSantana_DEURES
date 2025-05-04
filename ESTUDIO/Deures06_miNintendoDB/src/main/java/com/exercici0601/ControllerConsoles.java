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
 * Controlador para la vista de consolas de Nintendo.
 * Gestiona la visualización y selección de consolas con sus detalles.
 */
public class ControllerConsoles {

    // Elementos de la interfaz gráfica inyectados desde el FXML
    
    @FXML
    private ChoiceBox<String> ChoiceBoxConsola; // Menú desplegable para seleccionar consolas
    
    @FXML
    private Label lblname; // Muestra el nombre de la consola
    
    @FXML
    private Label lbldatte; // Muestra la fecha de lanzamiento
    
    @FXML
    private Label lblprocessador; // Muestra el procesador de la consola
    
    @FXML
    private Label lblUnitsSold; // Muestra las unidades vendidas
    
    @FXML
    private ImageView imageViewConsola; // Muestra la imagen de la consola
    
    @FXML
    private ImageView imgArrowBack; // Botón para volver al menú principal

    private JSONArray consoles; // Almacena los datos de las consolas cargados del JSON

    /**
     * Método de inicialización que se ejecuta al cargar la vista.
     * Carga los datos de las consolas y configura los componentes.
     */
    @FXML
    public void initialize() {
        try {
            // 1. Cargar el archivo JSON de consolas desde los recursos
            InputStream is = getClass().getResourceAsStream("/assets/data/consoles.json");
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            consoles = new JSONArray(content);

            // 2. Limpiar y llenar el ChoiceBox con los nombres de las consolas
            ChoiceBoxConsola.getItems().clear();
            for (int i = 0; i < consoles.length(); i++) {
                String consoleName = consoles.getJSONObject(i).getString("name");
                ChoiceBoxConsola.getItems().add(consoleName);
            }

            // 3. Configurar el listener para detectar cambios de selección
            ChoiceBoxConsola.setOnAction(e -> {
                String selectedConsole = ChoiceBoxConsola.getValue();
                if (selectedConsole != null) {
                    updateConsoleDetails(selectedConsole);
                }
            });

            // 4. Seleccionar y mostrar la primera consola por defecto (si existe)
            if (consoles.length() > 0) {
                ChoiceBoxConsola.setValue(consoles.getJSONObject(0).getString("name"));
                updateConsoleDetails(ChoiceBoxConsola.getValue());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar las consolas:");
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los detalles de la consola seleccionada.
     * @param consoleName Nombre de la consola cuyos detalles se mostrarán
     */
    private void updateConsoleDetails(String consoleName) {
        try {
            // Buscar la consola seleccionada en el JSON
            for (int i = 0; i < consoles.length(); i++) {
                JSONObject console = consoles.getJSONObject(i);
                if (console.getString("name").equals(consoleName)) {
                    // Actualizar los labels con la información de la consola
                    lblname.setText(console.getString("name"));
                    lbldatte.setText(console.getString("date"));
                    lblprocessador.setText(console.getString("procesador"));
                    lblUnitsSold.setText(String.valueOf(console.getInt("units_sold")));

                    // Cargar y mostrar la imagen de la consola
                    String imagePath = "/assets/images0601/" + console.getString("image");
                    InputStream imgStream = getClass().getResourceAsStream(imagePath);
                    if (imgStream != null) {
                        Image image = new Image(imgStream);
                        imageViewConsola.setImage(image);
                    } else {
                        System.err.println("No se pudo cargar la imagen: " + imagePath);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar detalles de la consola:");
            e.printStackTrace();
        }
    }

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
}