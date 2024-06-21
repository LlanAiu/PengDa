package com.llan.mahjongfunsies.ai.functions;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.ai.components.Feature;
import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;

public class MahjongModel implements Model{
    private int playerIndex;

    public MahjongModel(int playerIndex){
        this.playerIndex = playerIndex;
    }

    @Override
    public double getRewardOf(State state) {
        if(state.isTerminal()){
            if(((Feature) state).getWinningIndex() == playerIndex){
                return AIConstants.WIN_REWARD;
            } else if (((Feature) state).getWinningIndex() == Constants.DRAW){
                return AIConstants.DRAW_REWARD;
            } else {
                return AIConstants.LOSS_REWARD;
            }
        }
        return 0.0;
    }

    @Override
    public State getResultingStateOf(State state, Command action) {
        return ((Feature) state).apply(action);
    }
}
