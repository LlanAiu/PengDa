package com.llan.mahjongfunsies.ai.policies;

import com.llan.mahjongfunsies.ai.functions.MahjongValueFunction;
import com.llan.mahjongfunsies.ai.functions.ValueFunction;

public class SoftMahjongPolicy extends EpsilonGreedy{
    private int playerIndex;

    public SoftMahjongPolicy(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    @Override
    public ValueFunction getFunction() {
        return new MahjongValueFunction(playerIndex);
    }
}
