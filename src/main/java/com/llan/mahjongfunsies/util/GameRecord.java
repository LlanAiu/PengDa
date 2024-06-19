package com.llan.mahjongfunsies.util;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.State;

import java.util.HashMap;
import java.util.Map;

public class GameRecord {
    private Map<Integer, State> record;
    private int[] rewards;

    public GameRecord(){
        record = new HashMap<>();
        rewards = new int[Constants.NUM_PLAYERS];
    }

    public void record(int turnNumber, State state){
        record.put(turnNumber, state);
    }
}
