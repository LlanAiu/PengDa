package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hand extends Subdeck{

    public Hand(boolean isFirst){
        if(isFirst){
            cards = new ArrayList<>(Constants.STARTING_CARDS + 1);
        } else {
            cards = new ArrayList<>(Constants.STARTING_CARDS);
        }
    }

    public void play(int index){

    }

    //returns null if false, returns a list of winning cards if true
    public Optional<List<Card>> isOneAway(){
        List<Card> possibleHands = new ArrayList<>();
        List<Card> winningCards = new ArrayList<>();
        possibleHands.addAll(cards);
        for(Card card : Constants.allCards){
            possibleHands.addLast(card);
            if(isWinning(possibleHands)){
                winningCards.add(card);
            }
            possibleHands.removeLast();
        }
        if(winningCards.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(winningCards);
        }
    }

    public boolean isWinning(){
        return isWinning(cards);
    }

    public boolean isWinning(List<Card> hand){
        return false;
    }
}
