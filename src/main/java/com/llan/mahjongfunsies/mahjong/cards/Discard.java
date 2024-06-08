package com.llan.mahjongfunsies.mahjong.cards;

import java.util.ArrayList;

public class Discard extends Subdeck{
    private static Discard instance;

    public static Discard getInstance() {
        if(instance == null){
            instance = new Discard();
        }
        return instance;
    }

    private Discard(){
        cards = new ArrayList<>();
    }

    public Card removeLastPlayed(){
        return this.removeLast();
    }

    public Card getLastPlayed(){
        return cards.getLast();
    }

}
