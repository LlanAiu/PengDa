package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;
import com.llan.mahjongfunsies.mahjong.commands.PrioritizedPostMove;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.players.Computer;
import com.llan.mahjongfunsies.mahjong.players.Human;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.ui.Board;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.Triplet;

import java.util.*;
import java.util.function.Predicate;

public class TurnManager {
    private Player[] players;

    //replace with a mode enum, eventually
    private int humanIndex;
    private int currentTurnIndex;
    private int winningIndex = -1;

    private Map<DisplayUtil.Orientation, Player> playerOrientations;

    private static TurnManager instance;

    public static TurnManager getInstance(){
        if(instance == null){
            instance = new TurnManager();
        }
        return instance;
    }

    private TurnManager(){
        currentTurnIndex = 0;
        humanIndex = 0;
        players = new Player[Constants.NUM_PLAYERS];
        playerOrientations = new HashMap<>();
        for(int i = 0; i < players.length; i++){
            if(i == humanIndex){
                players[i] = new Human(i);
            } else {
                players[i] = new Computer(i);
            }
        }
        playerOrientations.put(DisplayUtil.Orientation.DOWN, players[0]);
        playerOrientations.put(DisplayUtil.Orientation.LEFT, players[1]);
        playerOrientations.put(DisplayUtil.Orientation.UP, players[2]);
        playerOrientations.put(DisplayUtil.Orientation.RIGHT, players[3]);
    }

    public Player getPlayerByOrientation(DisplayUtil.Orientation orientation){
        return playerOrientations.get(orientation);
    }

    public int getCurrentTurnIndex(){
        return currentTurnIndex;
    }

    public void reset(){
        currentTurnIndex = 0;
        winningIndex = -1;
        for(Player player : players){
            player.reset();
        }
    }

    public Optional<Command> pollCurrentTurn(){
        if(players[currentTurnIndex].trySelect()){
            Command move = players[currentTurnIndex].getSelectedMove();
            if(players[currentTurnIndex].checkLegalMove(move)){
                return Optional.of(move);
            }
        }
        return Optional.empty();
    }

    public void pollAll(){
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex).forEach(Player::trySelect);
    }

    public Optional<Command> getMoveByPriority(){
        List<Command> moves = new ArrayList<>();
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex).forEach(player -> moves.add(player.getSelectedMove()));
        moves.sort(Comparator.comparingInt(o -> ((PrioritizedPostMove) o).getPriority()));
        if(!(moves.getFirst() instanceof NullCommand)){
            System.out.println("Selected Command: " + moves.getFirst().toString());
            return Optional.of(moves.getFirst());
        }
        return Optional.empty();
    }

    //For current turn only
    public void setPlayableMoves(){
        players[currentTurnIndex].setPlayingMoves();
    }

    //for post turn only
    public void setPostPlayableMoves(Card lastPlayed){
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex).forEach(player -> player.setLegalPostMoves(lastPlayed, currentTurnIndex));
        players[currentTurnIndex].clearLegalMoves();
        Board.getInstance().updatePostMoves(players[humanIndex].getLegalMoves());
    }

    public void drawInitialHands(){
        Arrays.stream(players).forEach(Player::drawInitialHand);
    }

    public void drawCard(){
        players[currentTurnIndex].drawCard();
        players[currentTurnIndex].sortHand();
        if(players[currentTurnIndex].hasWon()){
            players[currentTurnIndex].reveal();
        }
    }

    public void addCardToPlayer(GameAction action, Card card, int index, Optional<Triplet> cards){
        players[index].addCard(card);
        players[index].reveal(players[index].getSetOf(action, card, cards));
        players[index].sortHand();
    }

    public void removeCardFromPlayer(Card card, int index){
        players[index].removeCard(card);
    }

    public void incrementTurn(){
        currentTurnIndex++;
        currentTurnIndex %= Constants.NUM_PLAYERS;
    }

    public void setCurrentTurnIndex(int index){
        currentTurnIndex = index;
    }

    public void checkWin(){
        for(Player player : players){
            if(player.hasWon()){
                winningIndex = player.getIndex();
            }
        }
    }

    public int hasWon(){
        checkWin();
        return winningIndex;
    }

    public Player getPlayerByIndex(int index){
        return players[index];
    }
}
