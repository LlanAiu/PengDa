package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.commands.PrioritizedPostMove;
import com.llan.mahjongfunsies.util.Episode;

public class Game implements Episode {

    public enum Stage {
        PREMOVE,
        CHECKING,
        POSTCHECKING;
    }

    private final TurnManager manager;
    private final Discard discard;
    private final Deck deck;
    private Stage stage;


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
        stage = Stage.PREMOVE;
    }

    @Override
    public void run() {
        if(stage.equals(Stage.PREMOVE)){
            preTurn();
        }
        var move = manager.pollCurrentTurn(stage);
        if(move.isPresent()){
            if(stage.equals(Stage.CHECKING)){
                move.ifPresent(Command::execute);
                if(stage.equals(Stage.CHECKING)){
                    postTurn();
                }
            } else if (stage.equals(stage.POSTCHECKING)){
                move.ifPresent(move1 -> {
                    if(!((PrioritizedPostMove) move1).isNull()){
                        move1.execute();
                    } else {
                        nextTurn();
                    }
                });
            }
        }
    }

    private void preTurn(){
        manager.setPlayableMoves();
        stage = Stage.CHECKING;
    }

    private void postTurn(){
        manager.setPostPlayableMoves(discard.getLastPlayed());
        stage = Stage.POSTCHECKING;
    }

    private void nextTurn(){
        manager.incrementTurn();
        manager.drawCard();
        stage = Stage.PREMOVE;
    }

    @Override
    public void end() {
        //post game weight updates, should they exist
    }

    @Override
    public boolean isFinished() {
        return deck.isEmpty() || manager.hasWon() != -1;
    }

    public void playCard(Card card, int index){
        manager.removeCardFromPlayer(card, index);
        discard.addCard(card);
    }

    //all instances of this results in it being said person's turn
    public void addLastCardToPlayer(int index){
        manager.addCardToPlayer(discard.removeLastPlayed(), index);
        manager.setCurrentTurnIndex(index);
        stage = Stage.PREMOVE;
    }
}
