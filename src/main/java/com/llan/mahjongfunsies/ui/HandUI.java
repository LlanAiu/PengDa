package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.util.DisplayUtil;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.util.function.Predicate;

public class HandUI {
    private final GridPane grid;
    private final Player player;
    private final DisplayUtil.Orientation orientation;
    private boolean currentTurn;
    private int selectedIndex = -1;
    private Card[] lastHand;

    public HandUI(DisplayUtil.Orientation orientation) {
        this.orientation = orientation;
        player = Gameflow.getPlayerByOrientation(orientation);
        grid = new GridPane();
        if (orientation == DisplayUtil.Orientation.UP || orientation == DisplayUtil.Orientation.DOWN) {
            for (int i = 0; i < Constants.STARTING_CARDS; i++) {
                grid.add(new IndexedPane(i), i, 0);
            }
        } else {
            for (int i = 0; i < Constants.STARTING_CARDS; i++) {
                grid.add(new IndexedPane(i), 0, i);
            }
        }
        grid.setGridLinesVisible(true);
        grid.setVgap(2);
        grid.setHgap(2);
        currentTurn = false;
    }

    public GridPane getGrid(){
        return grid;
    }

    public void displayHand(){
        for(int i = 0; i < player.getHand().length; i++){
            addCard(player.getHand()[i], Gameflow.getCurrentTurnIndex() == player.getIndex(), i);
        }
    }

    public void addCard(Card card, boolean override, int index){
        Node cardView = DisplayUtil.displayCard(card, orientation, override,
                index, index == selectedIndex);
        if(orientation == DisplayUtil.Orientation.UP || orientation == DisplayUtil.Orientation.DOWN){
            grid.add(cardView, index, 0);
        } else {
            grid.add(cardView, 0, index);
        }
        GridPane.setHalignment(cardView, HPos.CENTER);
        GridPane.setValignment(cardView, VPos.CENTER);
    }

    public void replaceCard(Card card, int index){
        grid.getChildren().removeIf(node -> ((IndexedPane) node).getIndex() == index);
        addCard(card, true, index);
    }

    public void clearGrid(){
        grid.getChildren().removeAll(grid.getChildren());
    }

    public void setSelectedIndex(int index){
        System.out.println("Changed Selected Index");
        int lastIndex = selectedIndex;
        this.selectedIndex = index;
        if(lastIndex != -1){
            replaceCard(player.getHand()[lastIndex], lastIndex);
        }
        replaceCard(player.getHand()[index], index);
    }

    public DisplayUtil.Orientation getOrientation(){
        return orientation;
    }
}
