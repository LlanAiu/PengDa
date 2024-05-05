package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hand extends Subdeck{

    public Hand(){
        cards = new ArrayList<>();
    }

    public Hand(boolean isFirst){
        if(isFirst){
            cards = new ArrayList<>(Constants.STARTING_CARDS + 1);
        } else {
            cards = new ArrayList<>(Constants.STARTING_CARDS);
        }
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
