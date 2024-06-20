package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.util.NumericMatrix;

public interface State {
    NumericMatrix getFeature(int playerIndex);
    boolean isTerminal();
}
