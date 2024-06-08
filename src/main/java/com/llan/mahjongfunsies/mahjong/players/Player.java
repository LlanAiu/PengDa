package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Hand;
import com.llan.mahjongfunsies.mahjong.commands.*;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.util.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Player {
    Hand hand;
    int index;
    Command move;
    List<Command> legalMoves;

    public Player(int index){
        this.index = index;
        hand = new Hand();
        move = new NullCommand();
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
    }

    public void addCard(Card card){
        hand.addCard(card);
    }

    public void removeCard(Card card){
        hand.removeCard(card);
    }

    //To be finished later, used for triple/straight/win/quad
    public void reveal(List<Card> cards){
        hand.reveal(cards);
    }

    public List<Card> getSetOf(GameAction action, Card card){
        return  hand.getSetOf(action, card);
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

    public void setLegalPostMoves(Card lastPlayed, int lastPlayerIndex){
        if(!legalMoves.isEmpty()){
            this.clearLegalMoves();
        }
        int num = hand.countIdentical(lastPlayed);
        if (num >= 2) {
            legalMoves.add(new Triple(index));
            if(num >= 3){
                legalMoves.add(new Quad(index));
            }
        }

        //redo this section to add all possible moves
        if (hand.canStraight(lastPlayed) && index == ((lastPlayerIndex + 1) % Constants.NUM_PLAYERS)) {
            legalMoves.add(new Straight(index));
        }
        hand.isOneAway().ifPresent(cards -> {
            if(cards.contains(lastPlayed)){
                legalMoves.add(new Win(index));
            }
        });
    }

    public void setPlayingMoves(){
        if(!legalMoves.isEmpty()){
            this.clearLegalMoves();
        }
        for(Card card : hand.readAll()){
            if(card.isHidden()){
                legalMoves.add(new PlayCard(card, index));
            }
        }
    }

    //leaves only the remaining moves that need disambiguation-ing
    public void filterStraightMoves(){
        legalMoves.removeIf(command -> !(command instanceof Straight));
    }

    public void clearLegalMoves(){
        legalMoves.removeAll(legalMoves);
        move = new NullCommand();
    }

    //also dummy method rn
    public List<Command> getLegalMoves(){
        return legalMoves;
    }

    public boolean checkLegalMove(Command move){
        return legalMoves.contains(move);
    }

    public Command getSelectedMove(){
        return move;
    }

    //TO BE REMOVED SOON
    public void play(){
        if(legalMoves.contains(move)){
            move.execute();
            move = new NullCommand();
            System.out.println("Move played");
        } else {
            System.out.println("Selected Move Is Not Legal");
        }
    }

    public abstract boolean shouldDisplay();

    public abstract boolean trySelect();
}
