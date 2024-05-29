package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.Observer;
import com.llan.mahjongfunsies.util.Subject;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class DiscardUI implements Observer {
    GridPane grid;
    private final Discard discard;
    private Card[] lastDisplayed;

    private static DiscardUI instance;

    public static DiscardUI getInstance(){
        if(instance == null){
            instance = new DiscardUI();
        }
        return instance;
    }

    private DiscardUI(){
        discard = Discard.getInstance();
        discard.addObserver(this);
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setVgap(DisplayConstants.gridGap);
        grid.setHgap(DisplayConstants.gridGap);
        for(int i = 0; i < 13; i++){
            grid.getColumnConstraints().add(new ColumnConstraints(DisplayConstants.cardWidth));
        }
        for(int i = 0; i < 10; i++){
            grid.getRowConstraints().add(new RowConstraints(DisplayConstants.cardLength));
        }
    }

    public GridPane getGrid(){
        return grid;
    }

    public void displayDiscard(){
        if(grid.getChildren().isEmpty() && lastDisplayed == null){
            for(int i = 0; i < discard.readAll().length; i++){
                this.addCard(discard.readAll()[i], i);
            }
            lastDisplayed = discard.readAll();
        }
    }

    public void removeCard(int cardIndex){
        grid.getChildren().removeIf((node) -> ((IndexedPane) node).getIndex() == cardIndex);
    }

    public void addCard(Card card, int cardIndex){
        Node cardView = DisplayUtil.displayCard(
                card,
                DisplayUtil.Orientation.DOWN,
                true,
                cardIndex,
                false
        );
        grid.add(cardView, cardIndex % 13, cardIndex / 13);
    }

    public void replaceCard(Card replace, int cardIndex){
        removeCard(cardIndex);
        addCard(replace, cardIndex);
    }

    public void updateDisplay(Card[] display){
        if(lastDisplayed != null) {
            int max = Math.max(display.length, lastDisplayed.length);
            for (int i = 0; i < max; i++) {
                if (i > display.length - 1) {
                    this.removeCard(i);
                } else if (i > lastDisplayed.length - 1) {
                    this.addCard(display[i], i);
                } else {
                    if (!display[i].displayEquals(lastDisplayed[i], true)) {
                        this.replaceCard(display[i], i);
                    }
                }
            }
            lastDisplayed = display;
        }
    }

    public void clear(){
        grid.getChildren().removeAll(grid.getChildren());
    }

    @Override
    public void update(Subject observable) {
        System.out.println("DiscardUI Updated");
        Card[] cards = ((Discard) observable).readAll();
        this.updateDisplay(cards);
    }
}
