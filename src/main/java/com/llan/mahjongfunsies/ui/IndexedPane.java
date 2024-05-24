package com.llan.mahjongfunsies.ui;

import javafx.scene.layout.Pane;

public class IndexedPane extends Pane {
    private final int index;

    public IndexedPane(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
}
