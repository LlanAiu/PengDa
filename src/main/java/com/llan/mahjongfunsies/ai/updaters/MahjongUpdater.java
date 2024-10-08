package com.llan.mahjongfunsies.ai.updaters;

import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.ai.data.DataSaver;
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
        NumericMatrix step = DataSaver.retrieve("step" + playerIndex + ".txt");
        if(step != null && !step.isEmpty()) {
            stepSizeCount = (int) step.convertToDouble();
        }
    }

    @Override
    public NumericMatrix getUpdate(List<Command> actions, State currentState, GameRecord record) {
        TurnRecord lastTurn = record.getLast(playerIndex);
        if(lastTurn == null){
            return null;
        }
        Command selected = policy.select(actions, currentState);
        double value = (function.rewardOf(currentState)
                + AIConstants.DISCOUNT * function.valueOf(currentState, selected)
                - function.valueOf(lastTurn.gameState(), lastTurn.selectedAction()));
        value *= getStepSize();
        NumericMatrix update = lastTurn.gameState().getFeature(playerIndex).scale(value).transpose();
        return update;
    }

    public void saveStepSize(){
        NumericMatrix stepSize = new NumericMatrix(1, 1);
        stepSize.setValue(stepSizeCount, 0, 0);
        DataSaver.save(stepSize, "step" + playerIndex + ".txt");
    }
}
