package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.MathUtil;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.List;

public class HumanUI extends HandUI{
    private int selectedIndex = -1;
    private PostMoveUI moveUI;
    private HBox box;

    public HumanUI(DisplayUtil.Orientation orientation){
        super(orientation);
        box = new HBox();
        moveUI = new PostMoveUI(GameController.getInstance().getPlayerByOrientation(orientation));
        box.getChildren().add(moveUI.getNode());
        box.getChildren().add(grid);
    }

    public void setSelectedIndex(int index){
        System.out.println("Changed Selected Index");
        int lastIndex = selectedIndex;
        this.selectedIndex = index;
        if(lastIndex != -1 && MathUtil.between(lastIndex, -1, player.getCards().length)){
            replaceCard(player.getCards()[lastIndex], lastIndex);
        }
        if(index != -1){
            replaceCard(player.getCards()[index], index);
        }
    }

    public void updatePostMoves(List<Command> moves){
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
