package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.players.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Prompter {
    private VBox box;
    private Button[] buttons;

    public Prompter(Player player){
        box = new VBox();
        buttons = new Button[3];
        box.getChildren().addAll(buttons);
    }

    public void onAction(){

    }
}
