package com.llan.mahjongfunsies.ai.policies;

import com.llan.mahjongfunsies.ai.functions.MahjongValueFunction;
import com.llan.mahjongfunsies.ai.functions.ValueFunction;

public class SoftMahjongPolicy extends EpsilonGreedy{
    private MahjongValueFunction valueFunction;

    public SoftMahjongPolicy(int playerIndex) {
        valueFunction = new MahjongValueFunction(playerIndex);
        setValueFunction();
    }

    public SoftMahjongPolicy(int playerIndex, boolean load){
        valueFunction = new MahjongValueFunction(playerIndex);
        setValueFunction();
        if(load){
            valueFunction.loadWeights(playerIndex + ".txt");
        }
    }

    @Override
    public ValueFunction getFunction() {
        return valueFunction;
    }
}
