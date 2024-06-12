package com.llan.mahjongfunsies.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EndScreen {
    AnchorPane root;
    Label text;

    public EndScreen(){
        root = new AnchorPane();
        text = new Label();
        root.getChildren().add(text);
        root.setPrefSize(400, 400);
    }

    public Parent getParent(){
        return root;
    }

    public void setText(int index){
        if(index != -1){
            text = new Label("Player " + index + " Wins!");
        } else {
            text = new Label("Draw");
        }
    }
}
