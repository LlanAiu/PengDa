package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DrawnQuad extends CommandBase{
    private boolean reveal;
    private List<Card> toReveal;
    private int setNumber;
    private Hand hand;

    public DrawnQuad(Hand hand, boolean reveal, List<Card> cards, int setNumber){
        this.reveal = reveal;
        toReveal = cards;
        this.setNumber = setNumber;
        this.hand = hand;
    }

    public DrawnQuad(){
        toReveal = new ArrayList<>();
    }

    @Override
    public void play() {
        if(reveal){
            hand.addCardsToRevealedSet(toReveal, setNumber);
        } else {
            hand.createSet(toReveal, setNumber);
        }
    }

    public boolean isSelected(){
        return !toReveal.isEmpty();
    }
}
