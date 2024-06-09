package com.llan.mahjongfunsies.mahjong.environment;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EndScreen {
    AnchorPane root;
    Label text;

    public EndScreen(int index){
        root = new AnchorPane();
        if(index != -1){
            text = new Label("Player " + index + " Wins!");
        } else {
            text = new Label("Draw");
        }
        root.getChildren().add(text);
        root.setPrefSize(400, 400);
    }

    public Parent getParent(){
        return root;
    }
}
