package com.llan.mahjongfunsies.mahjong;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.players.Player;

public class Gameflow {

    private static Deck deck = Deck.getInstance();
    private static Discard discardPile = Discard.getInstance();

    private static Card lastPlayed;

    private static Player[] players = new Player[Constants.NUM_PLAYERS];
    private static int firstTurnIndex = 0;
    private static int currentTurnIndex = 0;
    private static int turnNumber = 1;

    private Gameflow(){}

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

    public static void play(Card card, int index){
        discardPile.addCard(lastPlayed);
        players[index].removeCard(card);
        lastPlayed = card;
    }

    public static void addCardToPlayer(Card card, int index){
        players[index].addCard(card);
    }
}
