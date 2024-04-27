package com.llan.mahjongfunsies.mahjong;

import java.util.List;

public class Deck {
    private List<Card> cards;

    private static Deck instance;

    public static Deck getInstance(){
        if(instance == null){
            instance = new Deck();
        }
        return instance;
    }

    private Deck(){
        for(Card.Suit suit : Card.Suit.values()){
            for(int value = 1; value < 10; value++){
                for(int i = 0; i < 4; i++){
                    cards.add(new Card(suit, value));
                }
            }
        }
    }

    public void shuffle(){

    }
}
