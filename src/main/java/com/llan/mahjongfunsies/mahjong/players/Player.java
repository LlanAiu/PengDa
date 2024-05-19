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
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

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
        this.clearLegalMoves();
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

    //To be finished later
    public void finishSet(Card card){
        if(action == GameAction.TRIPLE || action == GameAction.QUAD){
            for(Card c : hand.getCards()){
                if(c.equals(card)){
                    c.setHidden(false);
                }
            }
        } else {

        }

        card.setHidden(false);
        hand.addCard(card);
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
        action = GameAction.NOTHING;
    }

    public GameAction moveSelected(){
        return action;
    }

    public void play(){
        action.play(selectedCard, index);
        action = GameAction.NOTHING;
    }

    public abstract Card select();
}
