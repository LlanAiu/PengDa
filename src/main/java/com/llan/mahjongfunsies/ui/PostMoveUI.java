package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.commands.*;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.players.Player;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;

public class PostMoveUI {
    private PostMoveButton[] buttons;

    private GridPane grid;

    public PostMoveUI(Player player){
        grid = new GridPane();
        buttons = new PostMoveButton[4];
        buttons[0] = new PostMoveButton(player, GameAction.TRIPLE);
        buttons[1] = new PostMoveButton(player, GameAction.STRAIGHT);
        buttons[2] = new PostMoveButton(player, GameAction.QUAD);
        buttons[3] = new PostMoveButton(player, GameAction.WIN);
        grid.add(buttons[0].getButton(), 0, 0);
        grid.add(buttons[1].getButton(), 0, 1);
        grid.add(buttons[2].getButton(), 1, 0);
        grid.add(buttons[3].getButton(), 1, 1);
        hideAll();
    }

    public Node getNode(){
        return grid;
    }

    public void hideAll(){
        Arrays.stream(buttons).forEach(button -> button.setVisible(false));
    }


    public void updateVisibilities(List<Command> legalMoves){
        for(Command move : legalMoves){
            if(move instanceof Triple){
                buttons[0].setVisible(true);
            } else if (move instanceof Straight){
                buttons[1].setVisible(true);
            } else if (move instanceof Quad){
                buttons[2].setVisible(true);
            } else if (move instanceof Win){
                buttons[3].setVisible(true);
            }
        }
    }
}
