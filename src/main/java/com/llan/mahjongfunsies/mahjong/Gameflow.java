package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.GameState;
import com.llan.mahjongfunsies.mahjong.environment.PlayerHand;
import com.llan.mahjongfunsies.mahjong.players.Computer;
import com.llan.mahjongfunsies.mahjong.players.Human;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.ui.Board;
import com.llan.mahjongfunsies.util.DisplayUtil;

import java.util.HashMap;
import java.util.Map;

public class Gameflow {

    private static Deck deck = Deck.getInstance();
    private static Discard discardPile = Discard.getInstance();

    private static Card lastPlayed;

    private static Map<DisplayUtil.Orientation, Player> playerOrientations;
    private static Player[] players = new Player[Constants.NUM_PLAYERS];
    private static int firstTurnIndex = 0;
    private static int currentTurnIndex = 0;
    private static int humanIndex = 0;
    private static boolean played = false;
    private static int turnNumber = 1;
    private static boolean shouldPlay = false;

    private static GameState state;

    private Gameflow(){}

    //returns true if move is played, false otherwise
    public static boolean pollNextTurn(){
        if(players[currentTurnIndex].actionSelected() != GameAction.NOTHING && shouldPlay){
            players[currentTurnIndex].play();
            return true;
        } else {
            players[currentTurnIndex].select();
            return false;
        }
    }

    public static void initialize(){
        for(int i = 0; i < players.length; i++){
            if(i == humanIndex){
                players[i] = new Human(i);
            } else {
                players[i] = new Computer(i);
            }
        }
        playerOrientations = new HashMap<>();
        updatePlayerOrientations();
    }

    //Static for now, maybe change later?
    public static void updatePlayerOrientations(){
        if(!playerOrientations.isEmpty()){
            playerOrientations.clear();
        }
        playerOrientations.put(DisplayUtil.Orientation.DOWN, players[0]);
        playerOrientations.put(DisplayUtil.Orientation.LEFT, players[1]);
        playerOrientations.put(DisplayUtil.Orientation.UP, players[2]);
        playerOrientations.put(DisplayUtil.Orientation.RIGHT, players[3]);
    }

    public static Player getPlayerByOrientation(DisplayUtil.Orientation orientation){
        return playerOrientations.get(orientation);
    }

    public static void reset(){
        currentTurnIndex = 0;
        firstTurnIndex = 0;
        turnNumber = 1;
        shouldPlay = false;
        played = false;
        lastPlayed = null;
        deck.reset();
        discardPile.clear();
        for(Player player : players){
            player.reset();
            player.drawInitialHand();
            player.sortHand();
        }
        players[firstTurnIndex].setPlayingMoves();
        updateState();
    }

    public static int getFirstTurnIndex(){
        return firstTurnIndex;
    }

    public static int getCurrentTurnIndex(){
        return currentTurnIndex;
    }

    public static int getHumanIndex() {
        return humanIndex;
    }

    public static void play(Card card, int index){
        System.out.println("Moved played: Player index: " + index + "; Card played: " + card);
        discardPile.addCard(card);
        players[index].removeCard(card);
        lastPlayed = card;
        played = true;
        shouldPlay = false;
        updateState();
        Board.getInstance().resetSelected();
        checkPostMoves();
        //wait three turns
        playPostMoves();
    }

    public static void checkPostMoves(){
        for(Player player : players){
            player.clearLegalMoves();
            player.setLegalMoves(lastPlayed, false);
        }
    }

    public static void playPostMoves(){
        int index = -1;
        int priority = 10;
        for(int i = 0; i < players.length; i++){
            if(players[i].actionSelected() != GameAction.NOTHING){
                if(players[i].actionSelected().getPriority() < priority){
                    priority = players[i].actionSelected().getPriority();
                    index = i;
                }
            }
        }
        if(index != -1){
            players[index].play();
        } else {
            passTurn();
        }
    }

    public static void passTurn(){
        currentTurnIndex++;
        currentTurnIndex = currentTurnIndex % Constants.NUM_PLAYERS;
        players[currentTurnIndex].drawCard();
        players[currentTurnIndex].clearLegalMoves();
        players[currentTurnIndex].setLegalMoves(lastPlayed, true);
    }

    public static void addCardToPlayer(Card card, int index){
        players[index].addCard(card);
        currentTurnIndex = index;
        players[index].setPlayingMoves();
    }

    public static void shouldPlay(){
        shouldPlay = true;
        System.out.println("Playing card now");
    }

    public static GameState getState(){
        return state;
    }

    public static void updateState(){
        PlayerHand[] hands = new PlayerHand[players.length];
        for(int i = 0; i < players.length; i++){
            DisplayUtil.Orientation orientation;
            switch(i){
                case 1 -> orientation = DisplayUtil.Orientation.LEFT;
                case 2 -> orientation = DisplayUtil.Orientation.UP;
                case 3 -> orientation = DisplayUtil.Orientation.RIGHT;
                default -> orientation = DisplayUtil.Orientation.DOWN;
            }
            hands[i] = new PlayerHand(i, players[i].getCards(), orientation);
        }
        state = new GameState(turnNumber, currentTurnIndex, lastPlayed, hands, discardPile.readAll(), deck.readAll());
    }
}
