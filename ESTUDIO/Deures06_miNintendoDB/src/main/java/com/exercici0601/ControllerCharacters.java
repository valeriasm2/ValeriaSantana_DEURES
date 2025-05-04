package com.exercici0601;

import com.utils.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Controlador para la vista de lista de personajes de Nintendo.
 * Implementa Initializable para inicialización automática al cargar la vista.
 */
public class ControllerCharacters implements Initializable {

    @FXML
    private ImageView imgArrowBack; // Icono de flecha para volver atrás

    @FXML
    private VBox list; // Contenedor principal para la lista de personajes

    /**
     * Método de inicialización automática al cargar la vista
     * 
     * @param url Ubicación relativa del objeto raíz
     * @param rb  Recursos para localizar objetos
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Cargar imagen de flecha de retorno
            URL imageURL = getClass().getResource("/assets/images0601/arrow-back.png");
            if (imageURL != null) {
                Image image = new Image(imageURL.toExternalForm());
                imgArrowBack.setImage(image);
            }
        } catch (Exception e) {
            System.err.println("Error loading image asset: arrow-back.png");
            e.printStackTrace();
        }
    }

    /**
     * Carga la lista de personajes desde el archivo JSON
     */
    public void loadList() {
        try {
            // Obtener ruta del archivo JSON
            URL jsonFileURL = getClass().getResource("/assets/data/characters.json");
            if (jsonFileURL != null) {
                // Leer contenido del JSON
                Path path = Paths.get(jsonFileURL.toURI());
                String content = Files.readString(path, StandardCharsets.UTF_8);
                JSONArray jsonInfo = new JSONArray(content);

                // Limpiar lista existente
                list.getChildren().clear();

                // Procesar cada personaje en el JSON
                for (int i = 0; i < jsonInfo.length(); i++) {
                    JSONObject character = jsonInfo.getJSONObject(i);
                    // Extraer datos del personaje
                    String name = character.getString("name");
                    String game = character.getString("game");
                    String imagePath = character.getString("image");
                    String color = character.getString("color");

                    // Crear y añadir ítem a la lista
                    HBox item = createCharacterItem(name, game, imagePath, color);
                    if (item != null) {
                        list.getChildren().add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un elemento visual para un personaje
     * @param name Nombre del personaje
     * @param game Juego al que pertenece
     * @param imagePath Ruta de la imagen
     * @param color Color asociado al personaje
     * @return AnchorPane con el diseño del personaje
     */
    private HBox createCharacterItem(String name, String game, String imagePath, String color) {
        try {
            // Crear contenedor principal del ítem
            HBox item = new HBox();
            item.setSpacing(15);
            item.setAlignment(Pos.CENTER_LEFT);
            item.setStyle(
                    "-fx-background-color: white; -fx-padding: 12; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 5, 0, 1, 1);");

            // Evento de clic
            item.setOnMouseClicked(event -> {
                try {
                    ControllerFitxa controller = (ControllerFitxa) UtilsViews.getController("viewPersonatgeFitxa");

                    if (controller == null) {
                        throw new Exception("No se pudo obtener el controlador de viewPersonatgeFitxa");
                    }

                    controller.setCharacterData(name, game, imagePath, color);
                    UtilsViews.setViewAnimating("viewPersonatgeFitxa");

                } catch (Exception e) {
                    System.err.println("Error al abrir ficha de personaje:");
                    e.printStackTrace();
                }
            });

            // Imagen del personaje
            ImageView imgCharacter = new ImageView();
            URL imageURL = getClass().getResource("/assets/images0601/" + imagePath);
            if (imageURL != null) {
                Image image = new Image(imageURL.toExternalForm());
                imgCharacter.setImage(image);
                imgCharacter.setFitHeight(50);
                imgCharacter.setFitWidth(50);
                imgCharacter.setPreserveRatio(true);
            }

            // Contenedor de texto
            VBox infoBox = new VBox(5);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            Label lblName = new Label(name);
            lblName.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #333;");

            Label lblGame = new Label(game);
            lblGame.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");

            infoBox.getChildren().addAll(lblName, lblGame);

            // Agregar al layout principal
            item.getChildren().addAll(imgCharacter, infoBox);

            return item;

        } catch (Exception e) {
            System.err.println("Error creando item de personaje: " + name);
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Maneja el evento de clic para volver a la vista principal
     * 
     * @param event Evento de ratón
     */
    @FXML
    private void toViewMain(MouseEvent event) {
        UtilsViews.setViewAnimating("ViewMain");
    }
}