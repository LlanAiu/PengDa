package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hand extends Subdeck{

    public Hand(){
        cards = new ArrayList<>(Constants.STARTING_CARDS);
    }

    public List<Card> findStraightPair(Card search){
        int value = search.value();
        Card.Suit suit = search.suit();
        if(suit == Card.Suit.HONOR){
            return List.of();
        }
        this.sort();
        List<Card> filtered = cards.stream().filter(card -> {
            if (card.suit() == suit && card.isHidden()){
               int distance = card.value() - value;
               return Math.abs(distance) <= 2 && distance != 0;
            }
            return false;
        }).distinct().toList();
        if(filtered.size() > 1){
            for(int i = 0; i < filtered.size() - 1; i++){
                int sum = (filtered.get(i).value() - value) + (filtered.get(i + 1).value() - value);
                if(Math.abs(sum) == 3 || sum == 0){
                    for(Card card : filtered){
                        if(!(card.equals(filtered.get(i)) || card.equals(filtered.get(i + 1)))){
                            filtered.remove(card);
                        }
                    }
                    return filtered;
                }
            }
        }
        return List.of();
    }

    public boolean canStraight(Card search){
        int value = search.value();
        Card.Suit suit = search.suit();
        if(suit == Card.Suit.HONOR){
            return false;
        }

        if(value - 2 >= 1){
            if(this.contains(Card.of(suit, value - 2)) && this.contains(Card.of(suit, value - 1))){
                return true;
            }
        }
        if(value + 1 <= 9 && value - 1 >= 1){
            if(this.contains(Card.of(suit, value - 1)) && this.contains(Card.of(suit, value + 1))){
                return true;
            }
        }
        if(value + 2 <= 9){
            if(this.contains(Card.of(suit, value + 1)) && this.contains(Card.of(suit, value + 2))){
                return true;
            }
        }
        return false;
    }

    //returns null if false, returns a list of winning cards if true
    public Optional<List<Card>> isOneAway(){
        Hand possibleHands = new Hand();
        List<Card> winningCards = new ArrayList<>();
        possibleHands.addCards(cards);
        for(Card card : Constants.allCards){
            possibleHands.addCard(card);
            if(CardUtil.isWinning(possibleHands)){
                winningCards.add(card);
            }
            possibleHands.removeCard(card);
        }
        if(winningCards.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(winningCards);
        }
    }

    public boolean isWinning(){
        return CardUtil.isWinning(this);
    }
}
