package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

public class Quad extends PrioritizedPostMove{
    public Quad(int playerIndex){
        super(playerIndex);
    }

    @Override
    int setPriority() {
        return Constants.QUAD_PRIORITY;
    }

    @Override
    GameAction getActionType() {
        return GameAction.QUAD;
    }
}
