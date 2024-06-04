package com.llan.mahjongfunsies.ui;

import javafx.geometry.Insets;

public final class DisplayConstants {
    public static final int cardWidth = 40;
    public static final int cardLength = 54;

    public static final int gridGap = 2;

    public static final Insets topInsets = new Insets(5, 75, 0, 75);
    public static final Insets bottomInsets = new Insets(0, 75, 5, 75);
    public static final Insets leftInsets = new Insets(20, 0, 20, 5);
    public static final Insets rightInsets = new Insets(20, 10, 20, 0);

    public static final int frameRateMillis = 50;
    public static final int postWaitMillis = 2500;
    //automatically floored
    public static final int maxPolledFrames = postWaitMillis / frameRateMillis;
}
