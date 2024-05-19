package com.llan.mahjongfunsies.controllers;

import com.llan.mahjongfunsies.ui.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    public void onStart(ActionEvent e){
        try {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            setScreen(stage);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void setScreen(Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(Main.class.getResource("game.fxml"));
        Parent root = Board.getInstance().getRoot();
        Scene scene = new Scene(root);
        GameController.getInstance().initialize();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

    }
}