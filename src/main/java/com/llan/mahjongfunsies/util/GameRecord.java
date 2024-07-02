package com.llan.mahjongfunsies.util;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class GameRecord {
    private List<TurnRecord>[] recordedTurns;
    private double[] rewards;

    public GameRecord(){
        recordedTurns = new List[Constants.NUM_PLAYERS];
        rewards = new double[Constants.NUM_PLAYERS];
        for(int i = 0; i < recordedTurns.length; i++){
            recordedTurns[i] = new ArrayList<>();
        }
    }

    public void record(int turnNumber, int playerIndex, State state, Command action, double reward){
        recordedTurns[playerIndex].addLast(new TurnRecord(turnNumber, state, action, reward));
    }

    public void setEndGameRewards(int winningIndex){
        if(winningIndex == -1){
            for(int i = 0; i < rewards.length; i++){
                rewards[i] = AIConstants.DRAW_REWARD;
            }
        } else {
            for(int i = 0; i < rewards.length; i++){
                if(i == winningIndex){
                    rewards[i] = AIConstants.WIN_REWARD;
                } else {
                    rewards[i] = AIConstants.LOSS_REWARD;
                }
            }
        }
    }

    public double[] getRewards(){
        return rewards;
    }

    public TurnRecord getLast(int playerIndex){
        TurnRecord[] last = getLast(playerIndex, 1);
        if(last == null){
            return null;
        } else {
            return last[0];
        }
    }

    //also in reverse order, e.g. last turn in index 0
    public TurnRecord[] getLast(int playerIndex, int numTurns){
        if(recordedTurns[playerIndex].size() < numTurns){
            return null;
        }
        TurnRecord[] turns = new TurnRecord[numTurns];
        int lastIndex = recordedTurns[playerIndex].size() - 1;
        for(int i = 0; i < numTurns; i++){
            turns[i] = recordedTurns[playerIndex].get(lastIndex - i);
        }
        return turns;
    }

}
