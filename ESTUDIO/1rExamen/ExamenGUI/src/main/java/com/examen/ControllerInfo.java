package com.examen;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.json.JSONArray;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class ControllerInfo implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    private int id = -1;
    private int previousId = -1;
    private int nextId = -1;

    private Button buttonPrevious;

    private Button buttonNext;

    private Text lblName;

    private Text lblYear;

    private Text lblMan;

    private Text lblCyl;

    private ImageView imgCar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Acció que s'executa quan el 'coiceBox' canvia de valor
            /* TODO
                - Obtenir el text sel·leccionat al choiceBox
                - Del text anterior, obtenir el nom del cotxe
                - Obtenir la id del cotxe consultant a la base de dades
                - Si existeix a la base de dades, amb l'id obtingut fer: load(id)
            */
        
    }

    /**
     * Carrega les dades d'un cotxe 
     * (o les del primer cotxe de la base de dades si id és -1)
     * @param id identificador del cotxe que es vol mostrar
     */
    public void load(int id) {
 
        this.id = getValidId(id);
        showCarData();
        configPrevious();
        configNext();
        configChoice();
    }

    /**
     * Retorna un 'id' vàlid segons la base de dades
     * @param id identificador que cal validar si existeix a la base de dades
     * @return 
     * - Si el valor de 'id' rebut com a paràmetre existeix a la base de dades, 
     *   retorna el mateix valor rebut com a paràmetre
     * - Si el valor de 'id' rebut com a paràmetre NO existeix a la base de dades, 
     *   retorna el primer valor de id vàlid de la base de dades
     */
    private int getValidId(int id) {
        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> carsList = db.query(String.format("SELECT * FROM cars WHERE id = '%d';", this.id));
        
        /* TODO
            - Si la consulta anterior ha retornat un element,
              retornar l'id rebut com a paràmetre
            - Si la consulta anterior no ha retornat cap element,
              consultar la base de dades per obtenir el primer id
              amb "SELECT * FROM cars LIMIT 1;"
              i retornar l'id de l'element obtingut.
        */
        return -1;
    }

    /**
     * Fent servir l'atribut "this.id", 
     * obté la informació del cotxe de la base de dades
     * mostra la informació a la vista:
     * - name
     * - manufacturer
     * - year
     * - cylinder
     * - image
     */
    private void showCarData() {
        
        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> carsList = db.query(String.format("SELECT * FROM cars WHERE id = '%d';", this.id));
        if (carsList.size() ==1 ) {
          HashMap<String, Object> car = carsList.get(0);
          String name = String.valueOf(this.id) + " " + (String) car.get("name");
          lblName.setText(name);
          lblMan.setText((String) car.get("manufacturer"));
          lblCyl.setText((String) car.get("cylinder"));
          lblYear.setText((String) car.get("year"));
          try {
            String imagePath = (String) car.get("image");
            Image image = new Image("file:" + imagePath);
            imgCar.setImage(image);
          } catch (Exception e) {
            System.err.println("Error loading image asset: " + (String) car.get("image"));
            e.printStackTrace();
          }

        }
    }

    /**
     * Defineix el valor de 'this.previousId'
     * a partir del valor actual de 'this.id'
     * fent servir les dades de la base de dades
     * 
     * Activa o desactiva el botó 'previous' segons
     * si hi ha un identificador previ disponible o no
     * 
     * Si no hi ha cap identificador anterior,
     * posa 'this.previousId' a -1
     */
    private void configPrevious() {

        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> carsList = db.query(String.format("SELECT * FROM cars WHERE id < '%d' ORDER BY id DESC LIMIT 1;", this.id));
        if (carsList.size() == 1) {
          HashMap<String, Object> cars = carsList.get(0);
          this.previousId = (int) cars.get(id);
          this.buttonPrevious.setDisable(false);
        } else {
          this.previousId = -1;
          this.buttonPrevious.setDisable(true);
        }
    }

    /**
     * Defineix el valor de 'this.nextId'
     * a partir del valor actual de 'this.id'
     * fent servir les dades de la base de dades
     * 
     * Activa o desactiva el botó 'next' segons
     * si hi ha un identificador previ disponible o no
     * 
     * Si no hi ha cap identificador anterior,
     * posa 'this.nextId' a -1
     */
    private void configNext() {

        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> carsList = db.query(String.format("SELECT * FROM cars WHERE id > '%d' ORDER BY id ASC LIMIT 1;", this.id));
        if (carsList.size() == 1) {
          HashMap<String, Object> carsNext = carsList.get(0);
          this.nextId = (int) carsNext.get(id);
          this.buttonNext.setDisable(false);
        } else {
          this.nextId = -1;
          this.buttonNext.setDisable(true);
        }
    }

    /**
     * Configura la llista desplegable
     * amb tots els cotxes 
     * que hi ha a la base de dades
     * els textos que mostra són "Fabricant Nom"
     * Per exemple:
     *  "Seat, Ibiza"
     *  "Citroën, DS"
     * 
     * Escull el cotxe amb identificador 'this.id'
     */
    private void configChoice() {

        AppData db = AppData.getInstance();
        String sql = "SELECT name, manufacturer FROM cars;";
        ArrayList<HashMap<String, Object>> rows = db.query(sql);

        /* TODO
        - Esborrar els elements del 'choiceBox'
        - Generar un ArrrayList<String> amb els textos que han
          de ser les opcions del coiceBox. El format és "manufacturer, name"
        - Definir els elements del 'choiceBox' a partir de l'ArrayList anterior
        - Escollir l'element seleccionat del 'choiceBox' 
          per tal que coincideixi amb 'this.id'
        */
    }

    /** 
     * S'executa amb el botó 'Previous'
     * 
     * Carrega les dades del cotxe anterior 
     * com a resultat de fer click 
     * sobre el botó 'previous'
     * 
     * Si no hi ha cap cotxe anterior, no fa res
     */
    @FXML
    public void previous(ActionEvent event) {
        if (this.previousId != -1) {
          load(this.id);
        }
    }

    /** 
     * S'executa amb el botó 'Next'
     * 
     * Carrega les dades del cotxe següent 
     * com a resultat de fer click 
     * sobre el botó 'next'
     * 
     * Si no hi ha cap cotxe següent, no fa res
     */
    @FXML
    public void next(ActionEvent event) {
        if (this.nextId != -1) {
          load(this.id);
        }
    }
}