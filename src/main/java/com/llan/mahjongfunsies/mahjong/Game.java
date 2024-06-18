package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.environment.End;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.Premove;
import com.llan.mahjongfunsies.mahjong.environment.Status;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.ui.Board;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.Episode;
import com.llan.mahjongfunsies.util.Triplet;

import java.util.Optional;

public class Game implements Episode {

    private final TurnManager manager;
    private final Discard discard;
    private final Deck deck;
    private Status status;

    public Game(){
        manager = new TurnManager();
        discard = Discard.getInstance();
        deck = Deck.getInstance();
    }

    @Override
    public void onStart() {
        manager.reset();
        discard.clear();
        deck.reset();
        manager.drawInitialHands();
        manager.drawCard();
        status = new Premove();
    }

    @Override
    public void run() {
        status.periodic();
        if(status.shouldTransition()){
            status.onTransition();
        }
    }

    public void preTurn(){
        manager.setPlayableMoves();
    }

    public void postTurn(){
        Board.getInstance().resetSelected();
        manager.setPostPlayableMoves(discard.getLastPlayed());
    }

    public void nextTurn(){
        manager.incrementTurn();
        drawCard();
    }

    public void drawCard(){
        manager.drawCard();
    }

    @Override
    public void end() {
        //post game weight updates, should they exist
        System.out.println("End method of Game called");
    }

    @Override
    public boolean isFinished() {
        return status instanceof End;
    }

    public Optional<Command> pollCurrentTurn(){
        return manager.pollCurrentTurn();
    }

    public void pollAll(){
        manager.pollAll();
    }

    public Optional<Command> getPostMove(){
        return manager.getMoveByPriority();
    }

    public boolean hasWon(){
        return manager.hasWon() != -1;
    }

    // -1 if no one has won
    public int getWinningIndex(){
        return manager.hasWon();
    }

    public boolean noCardsLeft(){
        return deck.isEmpty();
    }

    public void setState(Status status){
        this.status = status;
    }

    public String getState(){
        return status.getClass().getSimpleName();
    }

    public void playCard(Card card, int index){
        manager.removeCardFromPlayer(card, index);
        discard.addCard(card);
    }

    //all instances of this results in it being said person's turn
    public void addLastCardToPlayer(GameAction action, int index, Optional<Triplet> cards){
        manager.addCardToPlayer(action, discard.removeLast(), index, cards);
        manager.setCurrentTurnIndex(index);
        if(action.equals(GameAction.QUAD)){
            //technically supposed to draw from the end but that seems a little unnecessary for now
            manager.drawCard();
        }
    }

    public Player getPlayerByOrientation(DisplayUtil.Orientation orientation){
        return manager.getPlayerByOrientation(orientation);
    }

    public Player getPlayerByIndex(int index){
        return manager.getPlayerByIndex(index);
    }
}
