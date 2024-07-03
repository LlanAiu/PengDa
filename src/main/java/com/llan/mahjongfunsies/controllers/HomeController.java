package com.llan.mahjongfunsies.controllers;

import com.llan.mahjongfunsies.ui.DisplayConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    public void onStart(ActionEvent e){
        try {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GameController.getInstance().setTraining(false);
            start(stage, DisplayConstants.NORMAL_SCENE);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void onTrain(ActionEvent e){
        try {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GameController.getInstance().setTraining(true);
            setScreen(stage, DisplayConstants.TRAINING_SCENE);
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void start(Stage stage, Scene scene) throws IOException{
        GameController.getInstance().initialize();
        setScreen(stage, scene);
    }

    private void setScreen(Stage stage, Scene scene) throws IOException {
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}