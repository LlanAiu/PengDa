package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;

import java.util.ArrayList;

public class Hand extends Subdeck{

    public Hand(boolean isFirst){
        if(isFirst){
            cards = new ArrayList<>(Constants.STARTING_CARDS + 1);
        } else {
            cards = new ArrayList<>(Constants.STARTING_CARDS);
        }
    }

    public void play(int index){

    }

    public boolean isWinning(){


        return false;
    }
}
