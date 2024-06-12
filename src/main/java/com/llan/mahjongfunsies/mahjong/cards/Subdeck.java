package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.util.SubjectBase;

import java.util.ArrayList;
import java.util.List;

public abstract class Subdeck extends SubjectBase {
    List<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

    public void addCards(List<Card> cards){
        this.cards.addAll(cards);
    }

    public void removeCard(Card card){
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).exactEquals(card)){
                cards.remove(i);
                break;
            }
        }
    }

    public Card removeLast(){
        setChanged();
        return cards.removeLast();
    }

    public int countNonSetIdentical(Card search){
        return (int) this.filterOutSets(true).stream().filter(card -> card.equals(search)).count();
    }

    public int countIdentical(Card search){
        return (int) cards.stream().filter(card -> card.equals(search)).count();
    }

    public void clear(){
        cards.removeAll(cards);
    }

    public void sort(){
        cards.sort((card1, card2) -> {
            if(card1.getSetNumber() != card2.getSetNumber()){
                return -(card1.getSetNumber() - card2.getSetNumber());
            } else {
                if(card1.suit() != card2.suit()){
                    return card1.suit().getPriority() - card2.suit().getPriority();
                } else {
                    return card1.value() - card2.value();
                }
            }
        });
    }

    public List<Card> filterOutSets(boolean includeHiddenSets){
        if(includeHiddenSets){
            return cards.stream().filter(card -> !card.isPartOfSet()).toList();
        } else {
            return cards.stream().filter(card -> card.isHidden()).toList();
        }
    }

    public List<Card> findPairs(List<Card> cards){
        List<Card> pairs = new ArrayList<>();
        for(Card card : cards){
            int num = this.countNonSetIdentical(card);
            if(num >= 2 && !pairs.contains(card)){
                pairs.add(card);
            }
        }
        return pairs;
    }

    public Card[] readAll(){
        Card[] array = new Card[cards.size()];
        for(int i = 0; i < array.length; i++){
            array[i] = cards.get(i).clone();
        }
        return array;
    }

    public List<Card> getCards(){
        return cards;
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
