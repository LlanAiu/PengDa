package com.llan.mahjongfunsies.mahjong.cards;

import java.util.List;
import java.util.function.Predicate;

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

    public int countIdentical(Card search){
        return (int) cards.stream().filter(card -> card.equals(search)).count();
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
        return cards.toArray(new Card[cards.size()]);
    }

    @Override
    public String toString() {
        if(cards.isEmpty()){
            return "{ }";
        }
        final StringBuilder sb = new StringBuilder("{");
        for(Card card : cards){
            sb.append(card.toString() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append('}');
        return sb.toString();
    }
}
