package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Hand;

import java.util.ArrayList;
import java.util.List;

public class DrawnQuad extends CommandBase implements Ambiguous{
    private boolean reveal;
    private List<Card> toReveal;
    private int setNumber;
    private Hand hand;

    public DrawnQuad(Hand hand, int index, boolean reveal, List<Card> cards, int setNumber){
        super(index);
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

    @Override
    public boolean isSelected(){
        return !toReveal.isEmpty();
    }

    @Override
    public String getText() {
        return toReveal.getFirst().toString();
    }
}
