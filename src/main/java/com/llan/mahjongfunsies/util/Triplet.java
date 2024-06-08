package com.llan.mahjongfunsies.util;

import com.llan.mahjongfunsies.mahjong.cards.Card;

import java.util.Arrays;
import java.util.List;

public class Triplet {
    private final Card[] cards;

    public Triplet(Card card1, Card card2, Card card3){
        cards = new Card[] {card1, card2, card3};
    }

    public Triplet(Card[] cards){
        if(cards.length != 3){
            throw new IllegalArgumentException("Input array must have three elements");
        }
        this.cards = new Card[3];
        for(int i = 0; i < cards.length; i++){
            this.cards[i] = cards[i];
        }
    }

    public Card getCard(int index){
        return cards[index];
    }

    public List<Card> getCards(){
        return Arrays.stream(cards).toList();
    }
}
