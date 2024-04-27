package com.llan.mahjongfunsies.mahjong.cards;

import java.util.Comparator;
import java.util.List;

public abstract class Subdeck {
    List<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

    public void addCards(List<Card> cards){
        for(Card card : cards){
            this.cards.add(card);
        }
    }

    public void sort(){
        cards.sort((card1, card2) -> {
            if(card1.getSuit() != card2.getSuit()){
                return card1.getSuit().getPriority() - card2.getSuit().getPriority();
            } else {
                return card1.getValue() - card2.getValue();
            }
        });
    }

    public Card[] readAll(){
        Card[] readableCards = new Card[cards.size()];
        return cards.toArray(readableCards);
    }
}
