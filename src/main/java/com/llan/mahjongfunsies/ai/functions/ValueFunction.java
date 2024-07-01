package com.llan.mahjongfunsies.ai.functions;

import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.util.NumericMatrix;

public abstract class ValueFunction {
    NumericMatrix weights;

    public ValueFunction(){
        weights = new NumericMatrix(AIConstants.FEATURE_LENGTH, 1,
                AIConstants.STARTING_WEIGHT_MIN, AIConstants.STARTING_WEIGHT_MAX);
    }

    //w' = w + a[r + discount * q(s', a', w) - q(s, a, w)] * grad(q(s, a, w))
    public void updateWeights(NumericMatrix update){
        weights.plus(update);
    }

    public abstract double valueOf(State state);

    public abstract double valueOf(State state, Command command);

    public abstract double rewardOf(State state);

    public abstract double totalValueOf(State state, Command command);
}
