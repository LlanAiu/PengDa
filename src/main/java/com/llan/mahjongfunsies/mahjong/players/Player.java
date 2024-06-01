package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Hand;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.Move;
import com.llan.mahjongfunsies.util.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    Hand hand;
    int index;
    Move move;
    List<Move> legalMoves;

    public Player(int index){
        this.index = index;
        hand = new Hand();
        move = Move.none();
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
        hand.addCards(Deck.getInstance().drawNext(Constants.STARTING_CARDS));
    }

    public void drawCard(){
        hand.addCard(Deck.getInstance().drawNext());
        hand.sort();
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

    public Card[] getCards(){
        return hand.readAll();
    }

    public void sortHand(){
        hand.sort();
    }

    public void registerObserver(Observer observer){
        hand.addObserver(observer);
    }

    //playing is true if it's the current player's turn rather than just a move on the last card played
    public void setPostLegalMoves(Card lastPlayed){
        if(!legalMoves.isEmpty()){
            this.clearLegalMoves();
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
            if(card.isHidden()){
                legalMoves.add(new Move(GameAction.CARD, card, index));
            }
        }
    }

    public void clearLegalMoves(){
        legalMoves.removeAll(legalMoves);
        move = Move.none();
    }

    public boolean checkLegalMove(Move move){
        return legalMoves.contains(move);
    }

    public GameAction actionSelected(){
        return move.action();
    }

    public Move getSelectedMove(){
        return move;
    }

    public void play(){
        if(legalMoves.contains(move)){
            move.action().play(move.card(), move.playerIndex());
            move = Move.none();
            System.out.println("Move played");
        } else {
            System.out.println("Selected Move Is Not Legal");
        }
    }

    public abstract boolean trySelect();
}
