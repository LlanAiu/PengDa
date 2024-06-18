package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.mahjong.Game;

public class Trainer {
    private static Trainer instance;
    private Game currentGame;

    public static Trainer getInstance(){
        if(instance == null){
            instance = new Trainer();
        }
        return instance;
    }

    public Trainer(){

    }
}
