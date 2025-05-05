package com.examen;

import java.io.File;

import com.utils.UtilsViews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ControllerInfo {
    @FXML
    private Label title;
    @FXML
    private Label subtitle;
    @FXML
    private ImageView img;

    private int id;

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }
    public void setSubtitle(String subtitle){
        this.subtitle.setText(subtitle);
    }

    public void setImage(String imagePath){
        try {
            File file = new File("./data/images/"+imagePath);
            Image image = new Image(file.toURI().toString());
            this.img.setImage(image);
        } catch (NullPointerException e) {
            System.err.println("Error loading image asset: " + imagePath);
            e.printStackTrace();
        }
    }

    @FXML
    public void toViewCard(MouseEvent event) {
        ControllerForm ctrl = (ControllerForm) UtilsViews.getController("ViewForm");
        System.out.println(this.id);
        ctrl.setStatus("print",this.id);
        UtilsViews.setViewAnimating("ViewForm");
    }
}