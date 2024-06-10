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

    @Override
    public void addCard(Card card) {
        super.addCard(card);
        setChanged();
    }

    @Override
    public void addCards(List<Card> cards) {
        super.addCards(cards);
        setChanged();
    }

    @Override
    public void removeCard(Card card) {
        super.removeCard(card);
        setChanged();
    }

    public Card getLastPlayed(){
        return cards.getLast();
    }

}
