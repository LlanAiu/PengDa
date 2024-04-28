package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //returns null if false, returns a list of winning cards if true
    public Optional<List<Card>> isOneAway(){

        return Optional.empty();
    }

    public boolean isWinning(){


        return false;
    }
}
