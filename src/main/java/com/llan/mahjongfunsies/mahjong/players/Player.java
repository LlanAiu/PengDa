package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Hand;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private Hand hand;
    private int index;
    private Card selectedCard;
    private GameAction action;
    private List<Move> legalMoves;

    public Player(int index){
        this.index = index;
        hand = new Hand(index == Gameflow.getFirstTurnIndex());
        action = GameAction.NOTHING;
        legalMoves = new ArrayList<>();
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

    public int numIdenticalCards(Card search){
        return hand.countIdentical(search);
    }

    //playing is true if it's the current player's turn rather than just a move on the last card played
    public void setLegalMoves(Card lastPlayed, boolean playing){
        if(playing){

        }
    }

    public void clearLegalMoves(){
        legalMoves.removeAll(legalMoves);
    }

    public boolean moveSelected(){
        return action != GameAction.NOTHING;
    }

    public void play(){
        action.play(selectedCard, index);
    }

    public abstract Card select();
}
