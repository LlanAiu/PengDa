package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.commands.Ambiguous;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.players.Player;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.List;

public class Prompter {
    private VBox box;
    private Button[] buttons;
    private List<Command> moves;

    public Prompter(Player player){
        box = new VBox();
        moves = player.getLegalMoves();
        buttons = new Button[moves.size()];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button();
            assert moves.get(i) instanceof Ambiguous;
            buttons[i].setText(((Ambiguous) moves.get(i)).getText());
            int index = i;
            buttons[i].setOnAction(actionEvent -> onAction(index));
        }
        box.getChildren().addAll(buttons);
    }

    public Node getNode(){
        return box;
    }

    public void onAction(int index){
        InputHandler.getInstance().setLastInputMove((Ambiguous) moves.get(index));
    }
}
