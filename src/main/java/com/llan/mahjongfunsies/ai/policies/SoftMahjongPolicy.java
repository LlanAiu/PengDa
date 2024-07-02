package com.llan.mahjongfunsies.ai.policies;

import com.llan.mahjongfunsies.ai.functions.MahjongValueFunction;
import com.llan.mahjongfunsies.ai.functions.ValueFunction;

public class SoftMahjongPolicy extends EpsilonGreedy{
    private int playerIndex;
    private MahjongValueFunction valueFunction;

    public SoftMahjongPolicy(int playerIndex) {
        this.playerIndex = playerIndex;
        valueFunction = new MahjongValueFunction(playerIndex);
        setValueFunction();
    }

    @Override
    public ValueFunction getFunction() {
        return valueFunction;
    }
}
