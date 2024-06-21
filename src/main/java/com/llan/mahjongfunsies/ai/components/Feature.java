package com.llan.mahjongfunsies.ai.components;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.AIConstants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.util.NumericMatrix;

public class Feature implements State{
    private final NumericMatrix[] encodings;
    private final Card[][] hands;
    private final int currentTurnIndex;
    private final int remainingDeck;
    private final Card[] discard;
    private final boolean ended;
    private final int winningIndex;

    public Feature(Card[][] currentHands, int currentTurnIndex, int remainingDeck, Card[] discard, boolean ended, int winningIndex){
        this.hands = currentHands;
        this.currentTurnIndex = currentTurnIndex;
        this.remainingDeck = remainingDeck;
        this.discard = discard;
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
                encodings[i].setRow(encodingSubset(hands[j], j == i), 0, j * Constants.ALL_CARDS.length + 2);
            }
            encodings[i].setRow(discardEncoding, 0, hands.length * Constants.ALL_CARDS.length + 2);
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


    public Feature apply(Command action){
        //do something here dunno how yet
        return null;
    }

    private int[] encodingSubset(Card[] subdeck, boolean hiddenOverride) {
        int[] count = new int[Constants.ALL_CARDS.length];
        for(Card card : subdeck){
            if(!card.isHidden() || hiddenOverride){
                count[Constants.CARD_INDICES.get(card)]++;
            }
        }
        return count;
    }
}
