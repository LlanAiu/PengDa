package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.Premove;
import com.llan.mahjongfunsies.mahjong.environment.State;
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
    private State state;

    public Game(){
        manager = TurnManager.getInstance();
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
        state = new Premove();
    }

    @Override
    public void run() {
        state.periodic();
        if(state.shouldTransition()){
            state.onTransition();
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
        manager.drawCard();
    }

    @Override
    public void end() {
        //post game weight updates, should they exist
    }

    @Override
    public boolean isFinished() {
        return deck.isEmpty() || manager.hasWon() != -1;
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

    public void setState(State state){
        this.state = state;
    }

    public void playCard(Card card, int index){
        manager.removeCardFromPlayer(card, index);
        discard.addCard(card);
    }

    //all instances of this results in it being said person's turn
    public void addLastCardToPlayer(GameAction action, int index, Optional<Triplet> cards){
        manager.addCardToPlayer(action, discard.removeLastPlayed(), index, cards);
        manager.setCurrentTurnIndex(index);
    }

    public Player getPlayerByOrientation(DisplayUtil.Orientation orientation){
        return manager.getPlayerByOrientation(orientation);
    }

    public Player getPlayerByIndex(int index){
        return manager.getPlayerByIndex(index);
    }

    public int getCurrentTurnIndex(){
        return manager.getCurrentTurnIndex();
    }
}
