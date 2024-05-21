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
    Hand hand;
    int index;
    Card selectedCard;
    Move move;
    List<Move> legalMoves;

    public Player(int index){
        this.index = index;
        hand = new Hand(index == Gameflow.getFirstTurnIndex());
        move = null;
        legalMoves = new ArrayList<>();
    }

    public void reset(){
        hand.clear();
        this.clearLegalMoves();
    }

    public int getIndex(){
        return index;
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

    //To be finished later, used for triple/straight/win/quad
    public void finishSet(Card card){

    }

    public Card[] getHand(){
        return hand.readAll();
    }

    public void sortHand(){
        hand.sort();
    }

    //playing is true if it's the current player's turn rather than just a move on the last card played
    public void setLegalMoves(Card lastPlayed, boolean playing){
        if(!legalMoves.isEmpty()){
            this.clearLegalMoves();
        }
        if (playing) {
            for(Card card : hand.readAll()){
                legalMoves.add(new Move(GameAction.CARD, card, index));
            }
        }
        int num = hand.countIdentical(lastPlayed);
        if (num >= 2) {
            legalMoves.add(new Move(GameAction.TRIPLE, lastPlayed, index));
            if(num >= 3){
                legalMoves.add(new Move(GameAction.QUAD, lastPlayed, index));
            }
        }
        if (hand.canStraight(lastPlayed)) {
            legalMoves.add(new Move(GameAction.STRAIGHT, lastPlayed, index));
        }
    }

    public void setPlayingMoves(){
        if(!legalMoves.isEmpty()){
            this.clearLegalMoves();
        }
        for(Card card : hand.readAll()){
            legalMoves.add(new Move(GameAction.CARD, card, index));
        }
    }

    public void clearLegalMoves(){
        legalMoves.removeAll(legalMoves);
        move = Move.none();
    }

    public GameAction actionSelected(){
        return move.action();
    }

    public void play(){
        if(legalMoves.contains(move)){
            move.action().play(move.card(), move.playerIndex());
            move = Move.none();
        } else {
            System.out.println("Selected Move Is Not Legal");
        }
    }

    public abstract void select();
}
