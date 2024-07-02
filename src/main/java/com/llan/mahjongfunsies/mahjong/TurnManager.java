package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.ai.policies.RandomPolicy;
import com.llan.mahjongfunsies.ai.policies.SoftMahjongPolicy;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.commands.Command;
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
    public enum GameMode {
        TRAINING,
        NORMAL;
    }
    private Player[] players;

    //replace with a mode enum, eventually
    private GameMode mode;
    private int currentTurnIndex;
    private int winningIndex = -1;

    private List<PrioritizedPostMove> postMoves;
    private Map<DisplayUtil.Orientation, Player> playerOrientations;

    public TurnManager(GameMode mode){
        currentTurnIndex = 0;
        players = new Player[Constants.NUM_PLAYERS];
        postMoves = new ArrayList<>(4);
        playerOrientations = new HashMap<>();
        this.mode = mode;
        for(int i = 0; i < players.length; i++){
            if(mode == GameMode.NORMAL){
                if(i == Constants.HUMAN_INDEX){
                    players[i] = new Human(i);
                } else {
                    players[i] = new Computer(i, new RandomPolicy());
                }
            } else {
                players[i] = new Computer(i, new SoftMahjongPolicy(i));
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

    public Optional<Command> pollCurrentTurn(State gameState){
        if(players[currentTurnIndex].trySelect(gameState)){
            Command move = players[currentTurnIndex].getSelectedMove();
            if(players[currentTurnIndex].checkLegalMove(move)){
                return Optional.of(move);
            }
        }
        return Optional.empty();
    }

    public void pollAll(State gameState){
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex).forEach(player -> player.trySelect(gameState));
    }

    public Optional<Command> getMoveByPriority(){
        postMoves.sort(Comparator.comparingInt(o -> o.getPriority()));
        if(!postMoves.getFirst().isNull()){
            System.out.println("Selected Command: " + postMoves.getFirst().toString());
            return Optional.of(postMoves.getFirst());
        }
        return Optional.empty();
    }

    public void collectPostMoves(){
        postMoves.clear();
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex)
                .forEach(player -> postMoves.add((PrioritizedPostMove) player.getSelectedMove()));
    }

    public List<PrioritizedPostMove> getAllPostMoves(){
        return postMoves;
    }

    //For current turn only
    public void setPlayableMoves(){
        players[currentTurnIndex].setPlayingMoves();
        if(currentTurnIndex == Constants.HUMAN_INDEX && mode == GameMode.NORMAL){
            Board.getInstance().updateSetBasedMoves(players[Constants.HUMAN_INDEX].getLegalMoves());
        }
    }

    //for post turn only
    public void setPostPlayableMoves(Card lastPlayed){
        Arrays.stream(players).filter(player -> player.getIndex() != currentTurnIndex).forEach(player -> player.setLegalPostMoves(lastPlayed, currentTurnIndex));
        players[currentTurnIndex].clearLegalMoves();
        if(mode == GameMode.NORMAL){
            Board.getInstance().updateSetBasedMoves(players[Constants.HUMAN_INDEX].getLegalMoves());
        }
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

    public ArrayList<Card>[] getCards(){
        ArrayList<Card>[] hands = new ArrayList[Constants.NUM_PLAYERS];
        for(int i = 0; i < hands.length; i++){
            Card[] cards = players[i].getCards();
            hands[i] = new ArrayList<>(cards.length);
            hands[i].addAll(List.of(cards));
        }
        return hands;
    }

    //strictly training use only
    public Computer[] getPlayers(){
        if(mode == GameMode.NORMAL){
            throw new UnsupportedOperationException("Player Information Protected Under Normal Gameplay!");
        } else {
            Computer[] computers = new Computer[players.length];
            for(int i = 0; i < players.length; i++){
                computers[i] = (Computer) players[i];
            }
            return computers;
        }
    }
}
