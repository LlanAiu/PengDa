package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.commands.Straight;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.util.Triplet;
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
        box.getChildren().addAll(buttons);
        for(int i = 0; i < buttons.length; i++){
            assert moves.get(i) instanceof Straight;
            buttons[i].setText(getText((Straight) moves.get(i)));
            int index = i;
            buttons[i].setOnAction(actionEvent -> onAction(index));
        }
    }

    public Node getNode(){
        return box;
    }

    public void onAction(int index){
        InputHandler.getInstance().setLastInputMove((Straight) moves.get(index));
    }

    public String getText(Straight command){
        Triplet cards = command.getCards();
        StringBuilder sb = new StringBuilder("{ ");
        for(Card card : cards.getCards()){
            sb.append(card.value() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }");
        return sb.toString();
    }
}
