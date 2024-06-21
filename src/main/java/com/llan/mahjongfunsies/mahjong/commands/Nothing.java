package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

public class Nothing extends PrioritizedPostMove{

    public Nothing(int index){
        super(index);
    }

    @Override
    int setPriority() {
        return Constants.NULL_PRIORITY;
    }

    @Override
    GameAction getActionType() {
        return null;
    }
}
