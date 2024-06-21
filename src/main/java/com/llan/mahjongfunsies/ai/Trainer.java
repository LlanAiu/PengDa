package com.llan.mahjongfunsies.ai;

public class Trainer {
    private static Trainer instance;

    public static Trainer getInstance(){
        if(instance == null){
            instance = new Trainer();
        }
        return instance;
    }

    private Trainer(){

    }
}
