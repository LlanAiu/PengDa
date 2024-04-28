package com.llan.mahjongfunsies.mahjong.cards;

import java.util.List;

public abstract class Subdeck {
    List<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

    public void addCards(List<Card> cards){
        this.cards.addAll(cards);
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public void clear(){
        cards.removeAll(cards);
    }

    public void sort(){
        cards.sort((card1, card2) -> {
            if(card1.suit() != card2.suit()){
                return card1.suit().getPriority() - card2.suit().getPriority();
            } else {
                return card1.value() - card2.value();
            }
        });
    }

    public Card[] readAll(){
        Card[] readableCards = new Card[cards.size()];
        return cards.toArray(readableCards);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        for(Card card : cards){
            sb.append(card.toString() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append('}');
        return sb.toString();
    }
}
