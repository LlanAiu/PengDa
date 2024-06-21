package com.llan.mahjongfunsies.ai.functions;

import com.llan.mahjongfunsies.ai.components.State;

public class MahjongValueFunction extends ModelValueFunction{
    private int playerIndex;

    public MahjongValueFunction(int playerIndex){
        this.playerIndex = playerIndex;
    }

    @Override
    public Model getModel() {
        return new MahjongModel(playerIndex);
    }

    @Override
    public double stateValue(State state) {
        return state.getFeature(playerIndex).times(weights).convertToDouble();
    }
}
