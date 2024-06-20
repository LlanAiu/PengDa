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

public class TurnManager {
    private Player[] players;

    //replace with a mode enum, eventually
    private int humanIndex;
    private int currentTurnIndex;
    private int winningIndex = -1;

    private List<PrioritizedPostMove> postMoves;
    private Map<DisplayUtil.Orientation, Player> playerOrientations;

    public TurnManager(){
        currentTurnIndex = 0;
        humanIndex = 0;
        players = new Player[Constants.NUM_PLAYERS];
        postMoves = new ArrayList<>(4);
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
        postMoves.clear();
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex).forEach(player -> postMoves.add((PrioritizedPostMove) player.getSelectedMove()));
        postMoves.sort(Comparator.comparingInt(o -> o.getPriority()));
        if(!(postMoves.getFirst() instanceof NullCommand)){
            System.out.println("Selected Command: " + postMoves.getFirst().toString());
            return Optional.of(postMoves.getFirst());
        }
        return Optional.empty();
    }

    public List<PrioritizedPostMove> getAllPostMoves(){
        return postMoves;
    }

    //For current turn only
    public void setPlayableMoves(){
        players[currentTurnIndex].setPlayingMoves();
        if(currentTurnIndex == humanIndex){
            Board.getInstance().updateSetBasedMoves(players[humanIndex].getLegalMoves());
        }
    }

    //for post turn only
    public void setPostPlayableMoves(Card lastPlayed){
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex).forEach(player -> player.setLegalPostMoves(lastPlayed, currentTurnIndex));
        players[currentTurnIndex].clearLegalMoves();
        Board.getInstance().updateSetBasedMoves(players[humanIndex].getLegalMoves());
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
        players[index].endFirstMove();
    }

    public void nextPlayer(){
        currentTurnIndex++;
        currentTurnIndex %= Constants.NUM_PLAYERS;
    }

    public void setCurrentTurnIndex(int index){
        currentTurnIndex = index;
    }

    public int getCurrentTurnIndex(){
        return currentTurnIndex;
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

    public Card[][] getCards(){
        Card[][] hands = new Card[players.length][];
        for(int i = 0; i < hands.length; i++){
            hands[i] = players[i].getCards();
        }
        return hands;
    }
}
