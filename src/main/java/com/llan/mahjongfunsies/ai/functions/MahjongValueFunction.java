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
    public double valueOf(State state) {
        if(state.isTerminal()){
            return 0.0;
        }
        return state.getFeature(playerIndex).times(weights).convertToDouble();
    }

    @Override
    public double rewardOf(State state) {
        return model.getRewardOf(state);
    }
}
