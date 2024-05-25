package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.cards.Discard;
import javafx.scene.layout.GridPane;

public class DiscardUI {
    GridPane grid;
    private final Discard discard;

    private DiscardUI instance;

    public DiscardUI getInstance(){
        if(instance == null){
            instance = new DiscardUI();
        }
        return instance;
    }

    private DiscardUI(){
        discard = Discard.getInstance();
        grid = new GridPane();
        grid.setGridLinesVisible(true);
    }


}
