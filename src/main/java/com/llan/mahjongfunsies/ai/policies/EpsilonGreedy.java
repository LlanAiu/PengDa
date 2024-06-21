package com.llan.mahjongfunsies.ai.policies;

import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;
import com.llan.mahjongfunsies.util.MathUtil;

import java.util.List;

public abstract class EpsilonGreedy extends Policy{
    private double epsilon;

    public EpsilonGreedy(double epsilon){
        this.epsilon = MathUtil.clamp(epsilon, 0.0, 1.0);
    }

    public EpsilonGreedy(){
        this.epsilon = AIConstants.DEFAULT_EPSILON;
    }

    @Override
    public Command select(List<Command> actions, State currentState) {
        if(actions.isEmpty()){
            return new NullCommand();
        }
        if(Math.random() > epsilon){
            //take greedy action
            int index = -1;
            double maxValue = Double.MIN_VALUE;
            for(int i = 0; i < actions.size(); i++){
                double value = function.valueOf(currentState, actions.get(i));
                if(value > maxValue){
                    maxValue = value;
                    index = i;
                }
            }
            return actions.get(index);
        } else {
            //random
            int moveIndex = MathUtil.randInt(0, actions.size() - 1);
            return actions.get(moveIndex);
        }
    }
}
