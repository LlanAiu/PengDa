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
    private GridPane grid;
    private Player player;
    private DisplayUtil.Orientation orientation;
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
        currentTurn = false;
    }

    public GridPane getGrid(){
        return grid;
    }

    public void displayHand(int currentIndex){
        for(int i = 0; i < player.getHand().length; i++){
            addCard(player.getHand()[i], currentIndex == player.getIndex(), i);
        }
    }

    public boolean isDifferent(Card test, int index){
        return true;
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
        grid.getChildren().removeIf(new Predicate<Node>() {
            @Override
            public boolean test(Node node) {
               return ((IndexedPane) node).getIndex() == index;
            }
        });
        addCard(card, true, index);
    }
}
