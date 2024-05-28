package com.llan.mahjongfunsies.ui;

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

    private static DiscardUI instance;

    public static DiscardUI getInstance(){
        if(instance == null){
            instance = new DiscardUI();
        }
        return instance;
    }

    private DiscardUI(){
        discard = Discard.getInstance();
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

    public void updateDisplay(){
        if(grid.getChildren().isEmpty()){
            for(int i = 0; i < discard.readAll().length; i++){
                Node cardView = DisplayUtil.displayCard(
                        discard.readAll()[i],
                        DisplayUtil.Orientation.DOWN,
                        true,
                        i,
                        false
                );
                grid.add(cardView, i % 13, i / 13);
            }
        }
    }

    public void clear(){
        grid.getChildren().removeAll(grid.getChildren());
    }

    @Override
    public void update(Subject observable) {

    }
}
