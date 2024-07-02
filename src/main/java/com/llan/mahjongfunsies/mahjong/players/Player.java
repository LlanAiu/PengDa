package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.components.State;
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
    Card lastDrawnCard;
    boolean firstMove;

    public Player(int index){
        this.index = index;
        hand = new Hand();
        move = new NullCommand();
        legalMoves = new ArrayList<>();
        lastDrawnCard = Card.none();
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
        firstMove = true;
    }

    public void drawCard(){
        lastDrawnCard = Deck.getInstance().drawNext();
        hand.addCard(lastDrawnCard);
    }

    public void addCard(Card card){
        hand.addCard(card);
    }

    public void removeCard(Card card){
        hand.removeCard(card);
    }

    public void reveal(List<Card> cards){
        System.out.println("Un-hiding " + cards.size() + " cards from player " + index);
        System.out.println(cards);
        hand.reveal(cards);
    }

    public void reveal(){
        List<Card> cards = hand.filterOutSets(false);
        List<Card> modifiable = new ArrayList<>();
        for(Card card : cards){
            modifiable.add(card);
        }
        this.reveal(modifiable);
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
        this.clearLegalMoves();
        legalMoves.add(new Nothing(index));
        int num = hand.countNonSetIdentical(lastPlayed);
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
        this.clearLegalMoves();
        for(Card card : hand.readAll()){
            if(!card.isPartOfSet()){
                legalMoves.add(new PlayCard(card, index));
            }
        }
        if(hand.canDrawnQuad(lastDrawnCard)){
            legalMoves.add(hand.getDrawnQuadCommand(lastDrawnCard, index));
        }
        if(firstMove){
            List<Card> fours = hand.countFourOf();
            if(!fours.isEmpty()){
                for(Card card : fours){
                    legalMoves.add(hand.getDrawnQuadCommand(card, index));
                }
            }
        }
        lastDrawnCard = Card.none();
    }

    public void endFirstMove(){
        firstMove = false;
    }

    //leaves only the remaining moves that need disambiguation-ing
    public void filterAmbiguousMoves(){
        legalMoves.removeIf(command -> !(command instanceof Ambiguous));
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
        } else if (move instanceof DrawnQuad && !((DrawnQuad) move).isSelected()){
            List<Command> straights = legalMoves.stream().filter(command -> command instanceof Straight).toList();
            if(straights.size() == 1){
                move = straights.getFirst();
            }
        }
        return move;
    }

    public abstract boolean shouldDisplay();

    public abstract boolean trySelect(State gameState);
}
