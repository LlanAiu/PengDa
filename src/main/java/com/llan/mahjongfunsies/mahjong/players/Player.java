package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.Mahjong;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Hand;

public abstract class Player {
    private Hand hand;
    private int index;

    public Player(int index){
        this.index = index;
        hand = new Hand(index == Mahjong.getFirstTurnIndex());
    }

    public void reset(){
        hand.clear();
    }

    public boolean hasWon(){
        return hand.isWinning();
    }

    public void drawInitialHand(){
        if(index == Mahjong.getFirstTurnIndex()){
            hand.addCards(Deck.getInstance().drawNext(Constants.STARTING_CARDS + 1));
        } else {
            hand.addCards(Deck.getInstance().drawNext(Constants.STARTING_CARDS));
        }
    }

    public abstract Card play();
}
