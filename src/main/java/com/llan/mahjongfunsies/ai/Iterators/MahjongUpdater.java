package com.llan.mahjongfunsies.ai.Iterators;

import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.ai.policies.Policy;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.util.GameRecord;
import com.llan.mahjongfunsies.util.NumericMatrix;
import com.llan.mahjongfunsies.util.TurnRecord;

import java.util.List;

public class MahjongUpdater extends Updater{
    private int playerIndex;

    public MahjongUpdater(Policy policy, int playerIndex){
        super(policy);
        this.playerIndex = playerIndex;
    }

    //w' = w + a[r + discount * q(s', a', w) - q(s, a, w)] * grad(q(s, a, w))
    //MISSING STEP SIZE!!!, SHOULD BE VARIABLE THOUGH
    @Override
    public NumericMatrix getUpdate(List<Command> actions, State currentState, GameRecord record) {
        TurnRecord lastTurn = record.getLast(playerIndex);
        Command selected = policy.select(actions, currentState);
        double value = (function.rewardOf(currentState)
                + AIConstants.DISCOUNT * function.valueOf(currentState, selected)
                - function.valueOf(lastTurn.gameState(), lastTurn.selectedAction()));
        NumericMatrix update = lastTurn.gameState().getFeature(playerIndex).scale(value);
        return update;
    }
}
