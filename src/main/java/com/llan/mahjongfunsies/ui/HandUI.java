package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Hand;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.Observer;
import com.llan.mahjongfunsies.util.Subject;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public abstract class HandUI implements Observer {
    final GridPane grid;
    final Player player;
    final DisplayUtil.Orientation orientation;
    Card[] lastHand;

    public HandUI(DisplayUtil.Orientation orientation) {
        this.orientation = orientation;
        player = GameController.getInstance().getPlayerByOrientation(orientation);
        player.registerObserver(this);
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
        grid.setVgap(DisplayConstants.GRID_GAP);
        grid.setHgap(DisplayConstants.GRID_GAP);
    }

    public void displayHand(){
        if(lastHand == null){
            for(int i = 0; i < player.getCards().length; i++){
                addCard(player.getCards()[i], player.shouldDisplay(), i);
            }
            lastHand = player.getCards();
        } else {
            updateDisplay(player.getCards());
        }
    }

    public void addCard(Card card, boolean override, int index){
        Node cardView = DisplayUtil.displayCard(card, orientation, override,
                index, isSelected(index));
        if(orientation == DisplayUtil.Orientation.UP || orientation == DisplayUtil.Orientation.DOWN){
            grid.add(cardView, index, 0);
        } else {
            grid.add(cardView, 0, index);
        }
        GridPane.setHalignment(cardView, HPos.CENTER);
        GridPane.setValignment(cardView, VPos.CENTER);
    }

    public void removeCard(int index){
        grid.getChildren().removeIf(node -> ((IndexedPane) node).getIndex() == index);
    }

    public void replaceCard(Card card, int index){
        removeCard(index);
        addCard(card, player.shouldDisplay(), index);
    }

    public void clearGrid(){
        grid.getChildren().removeAll(grid.getChildren());
    }

    public DisplayUtil.Orientation getOrientation(){
        return orientation;
    }

    private void updateDisplay(Card[] display){
        if(lastHand != null) {
            int max = Math.max(display.length, lastHand.length);
            for (int i = 0; i < max; i++) {
                if (i > display.length - 1) {
                    this.removeCard(i);
                } else if (i > lastHand.length - 1) {
                    this.addCard(display[i], player.shouldDisplay(), i);
                } else {
                    if (!display[i].displayEquals(lastHand[i], player.shouldDisplay())) {
                        System.out.println("Replaced " + lastHand[i] + " at index " + i + " with " + display[i]);
                        replaceCard(display[i], i);
                    }
                }
            }
            lastHand = display;
        }
    }

    @Override
    public void update(Subject observable) {
//        System.out.println("Hand UI updated for index " + player.getIndex());
        Card[] currentCards = ((Hand) observable).readAll();
        this.updateDisplay(currentCards);
    }

    abstract boolean isSelected(int index);

    abstract Node getNode();
}
