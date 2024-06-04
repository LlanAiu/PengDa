package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.Game;
import com.llan.mahjongfunsies.mahjong.cards.Card;

import java.util.Objects;

public abstract class CommandBase implements Command{
    Card card;
    int playerIndex;
    Game currentGame;

    public CommandBase(Card card, int playerIndex){
        this.card = card;
        this.playerIndex = playerIndex;
        currentGame = GameController.getInstance().getCurrentGame();
    }

    @Override
    public void execute() {
        play();
    }

    public Card getCard(){
        return card;
    }

    public int getPlayerIndex(){
        return playerIndex;
    }

    public abstract void play();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandBase that = (CommandBase) o;
        return playerIndex == that.playerIndex && Objects.equals(card, that.card) && Objects.equals(currentGame, that.currentGame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card, playerIndex, currentGame);
    }
}
