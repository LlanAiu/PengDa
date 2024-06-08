package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Hand;
import com.llan.mahjongfunsies.mahjong.commands.*;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.util.Observer;
import com.llan.mahjongfunsies.util.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void reveal(List<Card> cards){
        hand.reveal(cards);
    }

    public List<Card> getSetOf(GameAction action, Card card, Optional<Triplet> cards){
        return hand.getSetOf(action, card, cards);
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
        int num = hand.countHiddenIdentical(lastPlayed);
        if (num >= 2) {
            legalMoves.add(new Triple(index));
            if(num >= 3){
                legalMoves.add(new Quad(index));
            }
        }

        List<Triplet> straights = hand.getStraights(lastPlayed);
        if (!straights.isEmpty() && index == ((lastPlayerIndex + 1) % Constants.NUM_PLAYERS)) {
            for(Triplet cards : straights){
                legalMoves.add(new Straight(index, cards));
            }
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

    public List<Command> getLegalMoves(){
        return legalMoves;
    }

    public boolean checkLegalMove(Command move){
        return legalMoves.contains(move);
    }

    public Command getSelectedMove(){
        if(move instanceof Straight && !((Straight) move).isSelected()){
            List<Command> straights = legalMoves.stream().filter(command -> command instanceof Straight).toList();
            if(straights.size() == 1){
                move = straights.getFirst();
            }
        }
        return move;
    }

    public abstract boolean shouldDisplay();

    public abstract boolean trySelect();
}
