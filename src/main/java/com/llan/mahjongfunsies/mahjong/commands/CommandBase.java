package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.Game;
import com.llan.mahjongfunsies.mahjong.cards.Card;

import java.util.Objects;

public abstract class CommandBase implements Command{
    Card card;
    int playerIndex;
    Game currentGame;

    public CommandBase(){}

    public CommandBase(int playerIndex){
        this.playerIndex = playerIndex;
        currentGame = GameController.getInstance().getCurrentGame();
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getName() + "{");
        sb.append("playerIndex=").append(playerIndex);
        sb.append(", card=").append(card);
        sb.append('}');
        return sb.toString();
    }
}
