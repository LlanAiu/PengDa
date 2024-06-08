package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.commands.PrioritizedPostMove;
import com.llan.mahjongfunsies.mahjong.commands.Straight;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.State;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.ui.Board;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.Episode;

import java.util.Optional;

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
        stage = Stage.PREMOVE;
    }

    @Override
    public void run() {
        if(stage.equals(Stage.PREMOVE)){
            preTurn();
        }
        var move = manager.pollCurrentTurn();
        if(move.isPresent()){
            if(stage.equals(Stage.CHECKING)){
                move.ifPresent(Command::execute);
                if(stage.equals(Stage.CHECKING)){
                    postTurn();
                }
            } else if (stage.equals(stage.POSTCHECKING)){
                move.ifPresent(move1 -> {
                    if(!((PrioritizedPostMove) move1).isNull()){
                        if(move1 instanceof Straight && !((Straight) move1).isSelected()){
                            //ok really really need to change to a state based design
                        } else {
                            move1.execute();
                        }
                    } else {
                        nextTurn();
                    }
                });
                Board.getInstance().resetPostMoves();
            }
        }
    }

    public void preTurn(){
        manager.setPlayableMoves();
        stage = Stage.CHECKING;
    }

    public void postTurn(){
        Board.getInstance().resetSelected();
        manager.setPostPlayableMoves(discard.getLastPlayed());
        stage = Stage.POSTCHECKING;
    }

    public void nextTurn(){
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
    public void addLastCardToPlayer(GameAction action, int index){
        manager.addCardToPlayer(action, discard.removeLastPlayed(), index);
        manager.setCurrentTurnIndex(index);
        stage = Stage.PREMOVE;
    }

    public Player getPlayerByOrientation(DisplayUtil.Orientation orientation){
        return manager.getPlayerByOrientation(orientation);
    }

    public void filterMoves(int index){
        manager.filterMoves(index);
    }

    public int getCurrentTurnIndex(){
        return manager.getCurrentTurnIndex();
    }
}
