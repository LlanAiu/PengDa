package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.MathUtil;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.List;

public class HumanUI extends HandUI{
    private int selectedIndex = -1;
    private PostMoveUI moveUI;
    private HBox box;
    private Button debugButton;

    public HumanUI(DisplayUtil.Orientation orientation){
        super(orientation);
        box = new HBox();
        debugButton = new Button("State");
        debugButton.setOnAction(action -> System.out.println("Current State: " + GameController.getInstance().getCurrentGame().getStatus()));
        moveUI = new PostMoveUI(GameController.getInstance().getPlayerByOrientation(orientation));
        box.getChildren().add(moveUI.getNode());
        box.getChildren().add(grid);
//        box.getChildren().add(debugButton);
    }

    public void setSelectedIndex(int index){
        int lastIndex = selectedIndex;
        this.selectedIndex = index;
        if(lastIndex != -1 && MathUtil.between(lastIndex, -1, player.getCards().length)){
            replaceCard(player.getCards()[lastIndex], lastIndex);
        }
        if(index != -1 && MathUtil.between(index, -1, player.getCards().length)){
            replaceCard(player.getCards()[index], index);
        }
    }

    public void updateSetBasedMoves(List<Command> moves){
        moveUI.updateVisibilities(moves);
    }

    public void hidePostMoves(){
        moveUI.hideAll();
    }

    public void resetSelected(){
        setSelectedIndex(-1);
    }

    @Override
    boolean isSelected(int index) {
        return index == selectedIndex;
    }

    @Override
    Node getNode() {
        return box;
    }
}
