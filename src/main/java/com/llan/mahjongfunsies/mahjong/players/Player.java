package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Hand;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

public abstract class Player {
    private Hand hand;
    private int index;
    private Card selectedCard;
    private GameAction action;

    public Player(int index){
        this.index = index;
        hand = new Hand(index == Gameflow.getFirstTurnIndex());
        action = GameAction.NOTHING;
    }

    public void reset(){
        hand.clear();
    }

    public boolean hasWon(){
        return hand.isWinning();
    }

    public void drawInitialHand(){
        if(index == Gameflow.getFirstTurnIndex()){
            hand.addCards(Deck.getInstance().drawNext(Constants.STARTING_CARDS + 1));
        } else {
            hand.addCards(Deck.getInstance().drawNext(Constants.STARTING_CARDS));
        }
    }

    public void addCard(Card card){
        hand.addCard(card);
    }

    public void removeCard(Card card){
        hand.removeCard(card);
    }

    public boolean moveSelected(){
        return action != GameAction.NOTHING;
    }

    public void play(){
        action.play(selectedCard, index);
    }

    public abstract Card select();
}
