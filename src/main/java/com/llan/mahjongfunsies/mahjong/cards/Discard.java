package com.llan.mahjongfunsies.mahjong.cards;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

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
        cards = FXCollections.observableArrayList();
    }

    @Override
    public void onChange(ListChangeListener.Change<? extends Card> change) {

    }
}
