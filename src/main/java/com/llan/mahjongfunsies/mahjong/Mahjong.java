package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.players.Player;

public class Mahjong {

    private static Deck deck = Deck.getInstance();
    private static Discard discardPile = Discard.getInstance();

    private static Player[] players = new Player[Constants.NUM_PLAYERS];
    private static int firstTurnIndex = 0;
    private static int currentTurnIndex = 0;
    private static int turnNumber = 1;

    private Mahjong(){}

    public static void play(){

    }

    public static void nextTurn(){

    }

    public static void reset(){
        currentTurnIndex = 0;
        turnNumber = 1;
        deck.reset();
        discardPile.clear();
        for(Player player : players){
            player.reset();
            player.drawInitialHand();
        }
    }

    public static int getFirstTurnIndex(){
        return firstTurnIndex;
    }

}
