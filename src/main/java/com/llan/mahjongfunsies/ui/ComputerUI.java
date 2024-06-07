package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.util.DisplayUtil;
import javafx.scene.Node;

public class ComputerUI extends HandUI{

    public ComputerUI(DisplayUtil.Orientation orientation){
        super(orientation);
    }

    @Override
    boolean isSelected(int index) {
        return false;
    }

    @Override
    Node getNode() {
        return grid;
    }
}
