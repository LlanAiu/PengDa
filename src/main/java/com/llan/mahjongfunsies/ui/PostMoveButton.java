package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.players.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class PostMoveButton {
    private Button button;
    private Player player;

    public PostMoveButton(Player player, GameAction action){
        this.player = player;
        String text;
        switch (action){
            case TRIPLE -> text = "碰";
            case QUAD -> text = "杠";
            case STRAIGHT -> text = "吃";
            default -> text = "胡";
        }
        button = new Button(text);
        button.setOnAction(onClick(action));
    }

    public void setVisible(boolean visible){
        button.setVisible(visible);
    }

    public Button getButton(){
        return button;
    }

    private EventHandler<ActionEvent> onClick(GameAction action){
        return actionEvent -> InputHandler.getInstance().onPostMove(action, player.getIndex());
    }


}
