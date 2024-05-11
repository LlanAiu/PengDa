package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.players.Human;
import com.llan.mahjongfunsies.mahjong.players.Player;

public class Gameflow {

    private static Deck deck = Deck.getInstance();
    private static Discard discardPile = Discard.getInstance();

    private static Card lastPlayed;

    private static Player[] players = new Player[Constants.NUM_PLAYERS];
    private static int firstTurnIndex = 0;
    private static int currentTurnIndex = 0;
    private static boolean played = false;
    private static int turnNumber = 1;

    private Gameflow(){}

    //returns true if move is played, false otherwise
    public static boolean pollNextTurn(){
        if(players[currentTurnIndex].moveSelected() != GameAction.NOTHING){
            players[currentTurnIndex].play();
            return true;
        }
        return false;
    }

    public static void initialize(){
        for(int i = 0; i < players.length; i++){
            players[i] = new Human(i);
        }
    }

    public static void reset(){
        currentTurnIndex = 0;
        firstTurnIndex = 0;
        turnNumber = 1;
        deck.reset();
        discardPile.clear();
        for(Player player : players){
            player.reset();
            player.drawInitialHand();
        }
        players[firstTurnIndex].setPlayingMoves();
    }

    public static int getFirstTurnIndex(){
        return firstTurnIndex;
    }

    public static void play(Card card, int index){
        discardPile.addCard(lastPlayed);
        players[index].removeCard(card);
        lastPlayed = card;
        played = true;
        checkPostMoves();
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
            if(players[i].moveSelected() != GameAction.NOTHING){
                if(players[i].moveSelected().getPriority() < priority){
                    priority = players[i].moveSelected().getPriority();
                    index = i;
                }
            }
        }
        if(index != -1){
            players[index].play();
        } else {
            currentTurnIndex++;
            currentTurnIndex = currentTurnIndex % Constants.NUM_PLAYERS;
        }
    }

    public static void addCardToPlayer(Card card, int index){
        players[index].addCard(card);
        currentTurnIndex = index;
        players[index].setPlayingMoves();
    }
}
