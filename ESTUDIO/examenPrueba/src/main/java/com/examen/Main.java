package com.examen;

import com.utils.UtilsViews;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// Fes anar l'exercici amb:
// ./run.sh com.examen.Main

public class Main extends Application {

    final int WINDOW_WIDTH = 600;
    final int WINDOW_HEIGHT = 400;
    final int MIN_WIDTH = 600;
    final int MIN_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws Exception {

        // Connectar amb la base de dades
        AppData db = AppData.getInstance();
        db.connect("./data/cars.sqlite");

        // Carrega la vista inicial des del fitxer FXML
        UtilsViews.parentContainer.setStyle("-fx-font: 14 arial;");
        // UtilsViews.addView(getClass(), "ViewInfo", "/assets/viewItem.fxml");
        UtilsViews.addView(getClass(), "ViewMenu", "/assets/viewList.fxml");
        UtilsViews.addView(getClass(), "ViewForm", "/assets/viewForm.fxml");

        // Carregar dades de la llista inicial
        // ControllerInfo crtl = (ControllerInfo) UtilsViews.getController("ViewInfo");
        // crtl.load(-1);

        // Mostrar la finestra
        Scene scene = new Scene(UtilsViews.parentContainer);
        stage.setScene(scene);
        stage.setTitle("Classic Cars App");
        stage.setMinWidth(MIN_WIDTH);
        stage.setWidth(WINDOW_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setHeight(WINDOW_HEIGHT);
        stage.show();

        // Afegeix una icona només si no és un Mac
        if (!System.getProperty("os.name").contains("Mac")) {
            Image icon = new Image("file:icons/icon.png");
            stage.getIcons().add(icon);
        }
    }

    // Aquesta funció es crida quan es tanca l'aplicació
    @Override
    public void stop() throws Exception {
        AppData db = AppData.getInstance();
        db.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
