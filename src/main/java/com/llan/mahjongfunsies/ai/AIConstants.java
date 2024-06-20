package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.Constants;

public final class AIConstants {
    public static final int FEATURE_LENGTH = Constants.ALL_CARDS.length * 5 + 2;
    public static final double DRAW_REWARD = 0.0;
    public static final double LOSS_REWARD = -1.0;
    public static final double WIN_REWARD = 1.0;
}
