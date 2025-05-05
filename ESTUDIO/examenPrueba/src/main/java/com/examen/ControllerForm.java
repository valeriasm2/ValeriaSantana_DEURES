package com.examen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerForm implements Initializable {

    //poner todas las variables del form
    @FXML
    private ImageView imgBackArrow;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldManufacturer;
    @FXML
    private TextField fieldYear;
    @FXML
    private ChoiceBox choiceCylinder;
    @FXML
    private ImageView imgCard;
    @FXML
    private Button buttonSelectFile;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;

    private int id;
    private String imageName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Càrrega de la imatge per la fletxa de retrocés
        try {
            URL imageURL = getClass().getResource("/assets/images/arrow-back.png");
            Image image = new Image(imageURL.toExternalForm());
            imgBackArrow.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading image asset: /assets/images/arrow-back.png");
            e.printStackTrace();
        }
    }
    //en el caso que se le dé a add tiene que estar limpio
    public void setStatus(String status,int id){
        AppData db = AppData.getInstance();
        this.id = id;
        choiceCylinder.getItems().clear();
        switch (status.toLowerCase()) {
            case "clear":
                fieldManufacturer.setText("");
                fieldName.setText("");
                fieldYear.setText("");
                imgCard.setImage(null);
                this.imageName = null;
                buttonUpdate.setDisable(true);
                buttonDelete.setDisable(true);
                buttonAdd.setDisable(false);
                ArrayList<HashMap<String,Object>> cylinder = db.query("SELECT cylinder FROM cars;");
                for (int i = 0; cylinder.size()>i;i++) {
                    String cylinderPutChoiceBox = (String) cylinder.get(i).get("cylinder");
                    choiceCylinder.getItems().add(cylinderPutChoiceBox);
                    choiceCylinder.getSelectionModel().selectFirst();
                }
            break;
            case "print":
                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);
                buttonAdd.setDisable(true);
                cylinder = db.query("SELECT cylinder FROM cars;");
                for (int i = 0; cylinder.size()>i;i++) {
                    String cylinderPutChoiceBox = (String) cylinder.get(i).get("cylinder");
                    choiceCylinder.getItems().add(cylinderPutChoiceBox);
                }

                ArrayList<HashMap<String,Object>> info = db.query("SELECT * FROM cars WHERE id="+this.id+";");
                for (int i = 0; info.size()>i; i++) {
                    String name = (String) info.get(i).get("name");
                    String manufacturer = (String) info.get(i).get("manufacturer");
                    Integer year = (Integer) info.get(i).get("year");
                    String cylinderInfo = (String) info.get(i).get("cylinder");
                    String img = (String) info.get(i).get("image");
                    fieldManufacturer.setText(name);
                    fieldName.setText(manufacturer);
                    fieldYear.setText(year.toString());
                    try {
                // Copia la imatge al directori de destinació
                        this.imageName = img;
                        String imagePath = System.getProperty("user.dir") + "/data/images/" + img;
                        File file = new File(imagePath);
                        Image image = new Image(file.toURI().toString());
                        this.imgCard.setImage(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        choiceCylinder.getSelectionModel().select(cylinderInfo);
                    }
            break;
        }


    }

    //en el caso que sea cualquier otro se muestra la información de ese elemento
    @FXML
    private void goBack(MouseEvent event) {
        try {
            ControllerMenu ctrl = (ControllerMenu) UtilsViews.getController("ViewMenu");
            ctrl.listarCars();
            UtilsViews.setViewAnimating("ViewMenu");
        } catch (IOException e){
            
        }
        
    }

    @FXML
    private void selectFile(MouseEvent event){
        Stage stage = (Stage) buttonSelectFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arxius imatge", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Copia l'imatge seleccionada a un directori de destinació
            String fileName = selectedFile.getName();
            this.imageName = fileName;
            String destination = System.getProperty("user.dir") + "/data/images/" + fileName;
            File destinationFile = new File(destination);
            
            try {
                // Copia la imatge al directori de destinació
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(destinationFile.toURI().toString());
                this.imgCard.setImage(image); // Mostra la imatge seleccionada
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private Integer createId(){
        AppData db = AppData.getInstance();
        Integer theLastId=0;
        ArrayList<HashMap<String,Object>> lastId = db.query("SELECT id FROM cars ORDER BY id DESC LIMIT 1;");
        System.out.println("lastId = "+lastId.get(0));
        if (!lastId.isEmpty()){
            theLastId = (Integer) lastId.get(0).get("id");
        }
        return theLastId+1;  
    }
    @FXML
    private void add (ActionEvent event){
        String name = fieldName.getText();
        String manufacturer= fieldManufacturer.getText();
        Integer year = null;
        try {
            year = Integer.parseInt(fieldYear.getText());
        } catch (NumberFormatException e) {
            System.out.println("el año es incorrecto");
            return;
        } 
        String seleccionadorCylinder = choiceCylinder.getSelectionModel().getSelectedItem().toString();
        String image = this.imageName;
        if (name==null||name.equals("")||manufacturer==null||manufacturer.equals("")||year==null){
            return;
        }
        this.id = createId();
        AppData db = AppData.getInstance();
        db.update(String.format("INSERT INTO cars (id,name,manufacturer,year,cylinder,image) VALUES ('%d','%s','%s','%d','%s','%s');",
        this.id,name,manufacturer,year,seleccionadorCylinder,image));
        setStatus("print", this.id);

    }

    @FXML
    private void update (ActionEvent event){
        String name = fieldName.getText();
        String manufacturer= fieldManufacturer.getText();
        Integer year = null;
        try {
            year = Integer.parseInt(fieldYear.getText());
        } catch (NumberFormatException e) {
            System.out.println("el año es incorrecto");
            return;
        } 
        String seleccionadorCylinder = choiceCylinder.getSelectionModel().getSelectedItem().toString();
        String image = this.imageName;
        if (name==null||name.equals("")||manufacturer==null||manufacturer.equals("")||year==null){
            return;
        }
        AppData db = AppData.getInstance();
        db.update(String.format("UPDATE cars SET id='%d',name='%s',manufacturer='%s',year='%d',cylinder='%s',image='%s' WHERE id='%d';",
        this.id,name,manufacturer,year,seleccionadorCylinder,image,this.id));
    }

    @FXML
    private void delete (ActionEvent event){
        
        AppData db = AppData.getInstance();
        db.update(String.format("DELETE FROM cars WHERE id='%d';",this.id));
        goBack(null);
    }

    }
