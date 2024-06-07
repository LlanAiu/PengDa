package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.commands.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class PostMoveUI {
    private Button[] buttons;

    private VBox box;

    public PostMoveUI(){
        box = new VBox();
        buttons = new Button[4];
        buttons[0] = new Button("碰");
        buttons[1] = new Button("吃");
        buttons[2] = new Button("杠");
        buttons[3] = new Button("胡");
        box.getChildren().addAll(buttons);
        hideAll();
    }

    public Node getNode(){
        return box;
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
