package com.llan.mahjongfunsies.mahjong.cards;

import java.util.ArrayList;
import java.util.List;

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
}
