package com.llan.mahjongfunsies.ai.components;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.commands.*;
import com.llan.mahjongfunsies.util.NumericMatrix;

import java.util.ArrayList;
import java.util.List;

public class Feature implements State{
    private final NumericMatrix[] encodings;
    private final ArrayList<Card>[] hands;
    private int currentTurnIndex;
    private int remainingDeck;
    private final ArrayList<Card> discard;
    private boolean ended;
    private int winningIndex;

    public Feature(List<Card>[] currentHands, int currentTurnIndex, int remainingDeck, List<Card> discard, boolean ended, int winningIndex){
        this.hands = (ArrayList<Card>[]) currentHands;
        this.currentTurnIndex = currentTurnIndex;
        this.remainingDeck = remainingDeck;
        this.discard = (ArrayList<Card>) discard;
        this.encodings = new NumericMatrix[Constants.NUM_PLAYERS];
        this.ended = ended;
        this.winningIndex = winningIndex;
        generateEncodings();
    }

    private void generateEncodings(){
        int[] discardEncoding = encodingSubset(discard, true);
        for(int i = 0; i < encodings.length; i++){
            encodings[i] = new NumericMatrix(1, AIConstants.FEATURE_LENGTH);
            encodings[i].setValue((currentTurnIndex == i) ? 1 : 0, 0, 0);
            encodings[i].setValue(remainingDeck, 0, 1);

            for(int j = 0; j < hands.length; j++){
                encodings[i].setValue(hands[j].size(), 0, j);
                encodings[i].setRow(encodingSubset(hands[j], j == i), 0, j * Constants.ALL_CARDS.length + 6);
            }
            encodings[i].setRow(discardEncoding, 0, hands.length * Constants.ALL_CARDS.length + 6);
        }
    }

    @Override
    public NumericMatrix getFeature(int playerIndex) {
        return encodings[playerIndex];
    }

    @Override
    public boolean isTerminal() {
        return ended;
    }

    public int getWinningIndex() {
        return winningIndex;
    }

    //should need to care about the hidden/not hidden part but not going to bc used by same player anyway
    public Feature apply(Command action){
        Feature changed = this.clone();
        Card card = ((CommandBase) action).getCard();
        if(action instanceof NullCommand || action instanceof Nothing) {
            return changed;
        } else if(action instanceof PlayCard){
            changed.discard.add(card);
            changed.hands[((PlayCard) action).getPlayerIndex()].remove(card);
            changed.currentTurnIndex = -1;
            changed.generateEncodings();
        } else if (action instanceof DrawnQuad){
            changed.hands[((DrawnQuad) action).getPlayerIndex()].add(Card.none());
            changed.currentTurnIndex = ((DrawnQuad) action).getPlayerIndex();
            changed.generateEncodings();
        } else if (action instanceof PrioritizedPostMove){
            changed.hands[((PrioritizedPostMove) action).getPlayerIndex()].add(card);
            changed.discard.remove(card);
            changed.currentTurnIndex = ((PrioritizedPostMove) action).getPlayerIndex();
            if(action instanceof Win){
                changed.winningIndex = ((Win) action).getPlayerIndex();
                changed.ended = true;
            } else if (action instanceof Quad){
                changed.hands[((Quad) action).getPlayerIndex()].add(Card.none());
            }
            changed.generateEncodings();
        }
        return changed;
    }

    private int[] encodingSubset(List<Card> subdeck, boolean hiddenOverride) {
        int[] count = new int[Constants.ALL_CARDS.length];
        for(Card card : subdeck){
            if(card.equals(Card.none())){
                continue;
            }
            if(!card.isHidden() || hiddenOverride){
                count[Constants.CARD_INDICES.get(card)]++;
            }
        }
        return count;
    }

    @Override
    protected Feature clone() {
        return new Feature(hands, currentTurnIndex, remainingDeck, discard, ended, winningIndex);
    }
}
