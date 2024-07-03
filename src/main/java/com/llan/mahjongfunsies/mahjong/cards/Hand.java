package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.commands.DrawnQuad;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.util.CardUtil;
import com.llan.mahjongfunsies.util.MathUtil;
import com.llan.mahjongfunsies.util.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Hand extends Subdeck{
    private int sets;

    public Hand(){
        cards = new ArrayList<>(Constants.STARTING_CARDS);
        sets = 0;
    }

    @Override
    public void removeCard(Card card) {
        super.removeCard(card);
        setChanged();
    }

    @Override
    public void sort() {
        super.sort();
        setChanged();
    }

    public void reveal(List<Card> reveal){
        boolean set = false;
        if ((MathUtil.between(reveal.size(), 2, 5))){
            set = true;
            sets++;
        }
        for(Card card : cards){
            if(!card.isHidden()){
                continue;
            }
            if(reveal.contains(card)){
                card.setHidden(false);
                if(set){
                    card.setSetNumber(sets);
                }
                reveal.remove(card);
            }
        }
    }

    //Doesn't reveal, pretty much just used for drawn sets of 4
    public void createSet(List<Card> cardSet, int setNumber) {
        List<Card> modifiable = new ArrayList<>();
        modifiable.addAll(cardSet);
        for(Card card : cards){
            if(card.isPartOfSet()){
                continue;
            }
            if(modifiable.contains(card)){
                card.setSetNumber(setNumber);
                modifiable.remove(card);
            }
        }
    }

    //same as above
    public void addCardsToRevealedSet(List<Card> cards, int setNumber){
        for(Card card : cards){
            card.setSetNumber(setNumber);
            card.setHidden(false);
        }
    }

    public List<Card> getSetOf(GameAction action, Card card, Optional<Triplet> triplet){
        List<Card> cards = new ArrayList<>(4);
        switch (action){
            case TRIPLE:
                for(int i = 0; i < 3; i++){
                    cards.add(Card.of(card.suit(), card.value(), card.honor()));
                }
                break;
            case QUAD:
                for(int i = 0; i < 4; i++){
                    cards.add(Card.of(card.suit(), card.value(), card.honor()));
                }
                break;
            case STRAIGHT:
                triplet.ifPresent(triplet1 -> cards.addAll(triplet1.getCards()));
                break;
            case WIN:
                cards.addAll(this.filterOutSets(false));
                break;
        }
        return cards;
    }

    public List<Triplet> getStraights(Card search){
        int value = search.value();
        Card.Suit suit = search.suit();
        List<Triplet> sets = new ArrayList<>();
        List<Card> filtered = this.filterOutSets(true);
        if(suit == Card.Suit.HONOR){
            return sets;
        }

        if(value - 2 >= 1){
            if(filtered.contains(Card.of(suit, value - 2)) && filtered.contains(Card.of(suit, value - 1))){
                sets.add(
                    new Triplet(
                        Card.of(suit, value - 2),
                        Card.of(suit, value - 1),
                        Card.of(suit, value)
                    )
                );
            }
        }
        if(value + 1 <= 9 && value - 1 >= 1){
            if(filtered.contains(Card.of(suit, value - 1)) && filtered.contains(Card.of(suit, value + 1))){
                sets.add(
                    new Triplet(
                        Card.of(suit, value - 1),
                        Card.of(suit, value),
                        Card.of(suit, value + 1)
                    )
                );
            }
        }
        if(value + 2 <= 9){
            if(filtered.contains(Card.of(suit, value + 1)) && filtered.contains(Card.of(suit, value + 2))){
                sets.add(
                    new Triplet(
                        Card.of(suit, value),
                        Card.of(suit, value + 1),
                        Card.of(suit, value + 2)
                    )
                );
            }
        }
        return sets;
    }

    //returns a list of winning cards if true
    public Optional<List<Card>> isOneAway(){
        Hand possibleHands = new Hand();
        List<Card> winningCards = new ArrayList<>();
        possibleHands.addCards(cards);
        for(Card card : Constants.ALL_CARDS){
            possibleHands.addCard(card);
            possibleHands.sort();
            if(CardUtil.isWinning(possibleHands)){
                winningCards.add(card);
            }
            possibleHands.removeCard(card);
        }
        if(winningCards.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(winningCards);
        }
    }

    public boolean canDrawnQuad(Card search){
        if(this.countIdentical(search) == 4){
            int differentSets = (int) cards.stream().filter(card -> card.equals(search)).flatMapToInt(card -> IntStream.of(card.getSetNumber())).distinct().count();
            if(differentSets <= 2){
                return true;
            }
        }
        return false;
    }

    public List<Card> countFourOf(){
        List<Card> fours = new ArrayList<>();
        for(Card card : cards){
            if(cards.stream().filter(card1 -> card1.equals(card)).count() == 4){
                if(!fours.contains(card)){
                    fours.add(card);
                }
            }
        }
        return fours;
    }

    public DrawnQuad getDrawnQuadCommand(Card search, int index){
        if(this.countNonSetIdentical(search) < 4){
            int setNumber = cards.stream().filter(card -> card.equals(search) && card.isPartOfSet()).toList().getFirst().getSetNumber();
            return new DrawnQuad(this, index, true, List.of(search), setNumber);
        } else {
            return new DrawnQuad(this, index, false, cards.stream().filter(card -> card.equals(search)).toList(), ++sets);
        }
    }

    public boolean isWinning(){
        return CardUtil.isWinning(this);
    }
}
