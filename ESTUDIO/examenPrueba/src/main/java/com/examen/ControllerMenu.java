package com.examen;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.utils.UtilsViews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControllerMenu implements Initializable {

    @FXML
    private Button add;
    @FXML
    private VBox list = new VBox();

    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listarCars();
        } catch (IOException e) {
            System.out.println("No se pueden cargar los coches");
        }
    }

    public void listarCars() throws IOException {
        
        URL resource = this.getClass().getResource("/assets/viewItem.fxml");

        // Netejar el contingut existent
        list.getChildren().clear();

        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> listaCars = db.query("SELECT * FROM cars;");
        for (int i = 0; listaCars.size() > i; i++) {
            int id = (int) listaCars.get(i).get("id");
            String name = (String) listaCars.get(i).get("name");
            String manufacturer = (String) listaCars.get(i).get("manufacturer");
            String image = (String) listaCars.get(i).get("image");
            System.out.println(image);

            // Carregar el template
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerInfo itemController = loader.getController();

            // Assignar els valors als controls del template
            itemController.setId(id);
            itemController.setTitle(name);
            itemController.setSubtitle(manufacturer);
            itemController.setImage(image);

            // Afegir el nou element a 'yPane'
            list.getChildren().add(itemTemplate);
        }
    }
    //setear la información creando una lista.
    //hacer una funcion llamada addCar, que derive al form vacio.
    public void addCar(){
        ControllerForm ctrl = (ControllerForm) UtilsViews.getController("ViewForm");
        ctrl.setStatus("clear",this.id);
        UtilsViews.setViewAnimating("ViewForm");
    }
}
